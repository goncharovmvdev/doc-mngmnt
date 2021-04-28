package doc.mngmnt.service.impl.mapper.document;

import doc.mngmnt.dto.document.AlreadyPresentDocumentDto;
import doc.mngmnt.dto.file.FileDto;
import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.entity.document.DocumentTypeEntity;
import doc.mngmnt.entity.file.FileEntity;
import doc.mngmnt.entity.user.UserEntity;
import doc.mngmnt.service.api.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class DocumentEntityAlreadyPresentDocumentDtoMapper implements Mapper<DocumentEntity, AlreadyPresentDocumentDto> {
    private final Mapper<FileEntity, FileDto> fileEntityFileDtoMapper;

    @Autowired
    public DocumentEntityAlreadyPresentDocumentDtoMapper(Mapper<FileEntity, FileDto> fileEntityFileDtoMapper) {
        this.fileEntityFileDtoMapper = fileEntityFileDtoMapper;
    }

    @Override
    public AlreadyPresentDocumentDto map(DocumentEntity from) {
        final AlreadyPresentDocumentDto alreadyPresentDocumentDto = new AlreadyPresentDocumentDto();
        alreadyPresentDocumentDto.setId(from.getId());
        alreadyPresentDocumentDto.setTitle(from.getTitle());
        alreadyPresentDocumentDto.setDescription(from.getDescription());
        alreadyPresentDocumentDto.setFilesDtos(this.mapFileEntitiesToFileDtos(from.getFiles()));
        alreadyPresentDocumentDto.setTypeNames(this.getDocumentTypeNameFromDocumentTypeEntities(from.getTypes()));
        alreadyPresentDocumentDto.setImportance(from.getImportance());
        alreadyPresentDocumentDto.setCatalogId(from.getCatalog().getId());
        alreadyPresentDocumentDto.setOwnerIds(this.getOwnerIdsFromOwnerEntities(from.getOwnerIds()));
        return alreadyPresentDocumentDto;
    }

    protected Set<FileDto> mapFileEntitiesToFileDtos(Set<FileEntity> fileEntities) {
        return fileEntities.stream()
            .map(fileEntityFileDtoMapper::map)
            .collect(toSet());
    }

    protected Set<String> getDocumentTypeNameFromDocumentTypeEntities(Set<DocumentTypeEntity> documentTypeEntities) {
        return documentTypeEntities.stream()
            .map(DocumentTypeEntity::getName)
            .collect(toSet());
    }

    protected Set<Long> getOwnerIdsFromOwnerEntities(Set<UserEntity> userEntities) {
        return userEntities.stream()
            .map(UserEntity::getId)
            .collect(toSet());
    }
}
