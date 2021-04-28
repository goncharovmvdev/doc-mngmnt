package doc.mngmnt.service.impl.mapper.file;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import doc.mngmnt.dto.file.FileDto;
import doc.mngmnt.entity.file.FileEntity;
import doc.mngmnt.service.api.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class FileEntityFileDtoMapper implements Mapper<FileEntity, FileDto> {
    private final Firestore firestore;

    @Autowired
    public FileEntityFileDtoMapper(Firestore firestore) {
        this.firestore = firestore;
    }

    // TODO: 28.04.2021 разобраться с фаербейсом(мне)
    @Override
    public FileDto map(FileEntity from) {
        @NotNull final String path = from.getPath();
        final DocumentReference document = firestore.document(path);

        FileDto fileDto = new FileDto();
        fileDto.setPath(path);
        return fileDto;
    }
}
