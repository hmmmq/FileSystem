package com.filesys.service.impl;

import com.filesys.entity.Document;
import com.filesys.mapper.DocumentMapper;
import com.filesys.service.IDocumentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author meiqi
 * @since 2024-10-25
 */
@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements IDocumentService {

}
