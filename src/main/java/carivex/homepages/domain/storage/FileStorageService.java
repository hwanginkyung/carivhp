package carivex.homepages.domain.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path root;

    public FileStorageService(@Value("${app.upload-dir:./uploads}") String uploadDir) {
        this.root = Path.of(uploadDir).toAbsolutePath().normalize();
    }

    public StoredFile storeSingle(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        Files.createDirectories(root);

        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "file" : file.getOriginalFilename());
        String ext = "";
        int idx = original.lastIndexOf('.');
        if (idx > -1) ext = original.substring(idx);

        String stored = UUID.randomUUID() + ext;
        Path target = root.resolve(stored);
        Files.copy(file.getInputStream(), target);

        return new StoredFile(original, stored);
    }

    /**
     * 본문 인라인 이미지 업로드 전용 저장.
     * 확장자 화이트리스트 + magic bytes 검증을 거친 이미지만 저장한다.
     */
    public StoredFile storeImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("이미지 파일이 비어 있습니다.");
        }

        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "image" : file.getOriginalFilename());
        String ext = "";
        int idx = original.lastIndexOf('.');
        if (idx > -1) ext = original.substring(idx).toLowerCase();

        if (!ALLOWED_IMAGE_EXT.contains(ext)) {
            throw new IllegalArgumentException("허용되지 않는 이미지 형식입니다. (jpg, jpeg, png, gif, webp만 가능)");
        }

        if (!isSupportedImage(readHead(file))) {
            throw new IllegalArgumentException("이미지 파일이 아닙니다.");
        }

        Files.createDirectories(root);
        String stored = UUID.randomUUID() + ext;
        Path target = root.resolve(stored);
        Files.copy(file.getInputStream(), target);

        return new StoredFile(original, stored);
    }

    public Path resolve(String storedName) {
        return root.resolve(storedName).normalize();
    }

    private static final java.util.Set<String> ALLOWED_IMAGE_EXT =
            java.util.Set.of(".jpg", ".jpeg", ".png", ".gif", ".webp");

    private byte[] readHead(MultipartFile file) throws IOException {
        try (var in = file.getInputStream()) {
            return in.readNBytes(12);
        }
    }

    /** 매직바이트로 JPEG/PNG/GIF/WEBP 여부를 검증한다. */
    private boolean isSupportedImage(byte[] b) {
        if (b == null || b.length < 12) return false;
        // JPEG: FF D8 FF
        if ((b[0] & 0xFF) == 0xFF && (b[1] & 0xFF) == 0xD8 && (b[2] & 0xFF) == 0xFF) return true;
        // PNG: 89 50 4E 47
        if ((b[0] & 0xFF) == 0x89 && b[1] == 'P' && b[2] == 'N' && b[3] == 'G') return true;
        // GIF: GIF8(7a/9a)
        if (b[0] == 'G' && b[1] == 'I' && b[2] == 'F' && b[3] == '8') return true;
        // WEBP: RIFF....WEBP
        if (b[0] == 'R' && b[1] == 'I' && b[2] == 'F' && b[3] == 'F'
                && b[8] == 'W' && b[9] == 'E' && b[10] == 'B' && b[11] == 'P') return true;
        return false;
    }
}
