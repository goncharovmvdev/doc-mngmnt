package doc.mngmnt.service.impl.document;

import doc.mngmnt.dto.document.SaveDocumentDto;
import doc.mngmnt.dto.document.AlreadyPresentDocumentDto;
import doc.mngmnt.entity.document.DocumentEntity;
import doc.mngmnt.repository.document.DocumentRepository;
import doc.mngmnt.service.api.document.DocumentService;
import doc.mngmnt.service.api.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final Mapper<SaveDocumentDto, DocumentEntity> documentEntitySaveDocumentDtoMapper;
    private final Mapper<AlreadyPresentDocumentDto, DocumentEntity> documentEntityUpdateDocumentDtoMapper;
    private final Mapper<DocumentEntity, AlreadyPresentDocumentDto> documentEntityAlreadyPresentDocumentDtoMapper;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository,
        Mapper<SaveDocumentDto, DocumentEntity> documentEntitySaveDocumentDtoMapper,
        Mapper<AlreadyPresentDocumentDto, DocumentEntity> documentEntityUpdateDocumentDtoMapper,
        Mapper<DocumentEntity, AlreadyPresentDocumentDto> documentEntityAlreadyPresentDocumentDtoMapper) {

        this.documentRepository = documentRepository;
        this.documentEntitySaveDocumentDtoMapper = documentEntitySaveDocumentDtoMapper;
        this.documentEntityUpdateDocumentDtoMapper = documentEntityUpdateDocumentDtoMapper;
        this.documentEntityAlreadyPresentDocumentDtoMapper = documentEntityAlreadyPresentDocumentDtoMapper;
    }

    @Override
    public SaveDocumentDto save(SaveDocumentDto saveDocumentDto) {
        DocumentEntity entity = documentEntitySaveDocumentDtoMapper.map(saveDocumentDto);
        documentRepository.save(entity);
        return saveDocumentDto;
    }

    @Override
    public AlreadyPresentDocumentDto update(AlreadyPresentDocumentDto alreadyPresentDocumentDto) {
        final DocumentEntity documentEntity = documentEntityUpdateDocumentDtoMapper.map(alreadyPresentDocumentDto);
        documentRepository.save(documentEntity);
        return alreadyPresentDocumentDto;
    }

    @Override
    public AlreadyPresentDocumentDto findById(Long id) {
        final DocumentEntity documentEntity = documentRepository.findById(id).orElseThrow();
        return documentEntityAlreadyPresentDocumentDtoMapper.map(documentEntity);
    }
}
