package doc.mngmnt.service.impl.mapper.file;

import doc.mngmnt.dto.file.FileDto;
import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.file.FileEntity;
import doc.mngmnt.repository.document.DocumentRepository;
import doc.mngmnt.service.api.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileDtoFileEntityMapper implements Mapper<FileDto, FileEntity> {
    private final DocumentRepository documentRepository;

    @Autowired
    public FileDtoFileEntityMapper(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public FileEntity map(FileDto from) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath(from.getPath());
        if (from.getDocumentId() != null) {
            fileEntity.setDocumentId(this.getDocumentEntityById(from.getDocumentId()));
        }
        return fileEntity;
    }

    protected DocumentEntity getDocumentEntityById(Long id) {
        return documentRepository.findById(id)
            .orElseThrow();
    }
}
