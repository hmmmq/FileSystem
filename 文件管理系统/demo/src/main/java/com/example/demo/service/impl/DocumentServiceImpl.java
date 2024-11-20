package com.example.demo.service.impl;

import com.example.demo.entity.Document;
import com.example.demo.mapper.DocumentMapper;
import com.example.demo.service.IDocumentService;
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
