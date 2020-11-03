package com.iss.importer;

import com.sun.xml.xsom.impl.scd.Iterators;
import org.apache.avro.generic.GenericData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.platform.filemanager.api.FileImporterContext;
import org.nuxeo.ecm.platform.filemanager.service.extension.AbstractFileImporter;

import javax.swing.text.Document;
import java.io.IOException;
import java.io.Serializable;

public class PhotoImporter extends AbstractFileImporter {
    private static final Log log = LogFactory.getLog(PhotoImporter.class);

    @Override
    public DocumentModel createOrUpdate(FileImporterContext context) throws IOException {
        String parentPath = context.getParentPath();
        PathRef parentRef = new PathRef(parentPath);
        CoreSession session = context.getSession();
        DocumentModel parentDoc = session.getDocument(parentRef);
        DocumentModel doc = null;
        String docType = parentDoc.getType();
        log.warn("docType=" +docType);
        switch (parentDoc.getType()) {
            case "PhotoAssetRequest":
                doc = createPhoto(context,parentDoc);
                break;
            default:
                break;
        }
        if (doc != null) {
            doc = session.createDocument(doc);
        }
        return doc;
    }

    protected  DocumentModel createPhoto(FileImporterContext context, DocumentModel parentDoc){
        Blob blob = context.getBlob();
        String fileName = StringUtils.defaultIfBlank(context.getFileName(), blob.getFilename());
        log.warn("createPhoto:fileName=" +fileName);
        CoreSession session = context.getSession();
        String parentPath = context.getParentPath();
        log.warn("createPhoto:parentPath=" +parentPath);
        DocumentModel photoDoc = session.createDocumentModel(parentPath, fileName,"photo");
        photoDoc.setPropertyValue("dc:title", fileName);
        photoDoc.setPropertyValue("file:content", (Serializable) blob);

        photoDoc.setPropertyValue("phi:aspectRadio",parentDoc.getPropertyValue("pa:aspectRatio"));
        photoDoc.setPropertyValue("phi:shotAngle",parentDoc.getPropertyValue("pa:shotAngle"));

        photoDoc.setPropertyValue("ai:subAssetTP",((String[])parentDoc.getPropertyValue("pa:imageTypes"))[0]);
        photoDoc.setPropertyValue("ai:background",parentDoc.getPropertyValue("pa:background"));
        photoDoc.setPropertyValue("ai:upcCodeText",parentDoc.getPropertyValue("pa:upcCode"));

        //Usage Rights
        photoDoc.setPropertyValue("ai:imageExpiration",parentDoc.getPropertyValue("ai:imageExpiration"));
        /*
        photoDoc.setPropertyValue("ai:imageExpiration.imageUsageRight",parentDoc.getPropertyValue("ai:imageExpiration.imageUsageRight"));
        photoDoc.setPropertyValue("ai:imageExpiration.imageMediaUsageRight",parentDoc.getPropertyValue("ai:imageExpiration.imageMediaUsageRight"));
        photoDoc.setPropertyValue("ai:imageExpiration.imageUsageExpirationDateStart",parentDoc.getPropertyValue("ai:imageExpiration.imageUsageExpirationDateStart"));
        photoDoc.setPropertyValue("ai:imageExpiration.imageUsageExpirationDateEnd",parentDoc.getPropertyValue("ai:imageExpiration.imageUsageExpirationDateEnd"));
        photoDoc.setPropertyValue("ai:imageExpiration.noImageUsageExpirationDate",parentDoc.getPropertyValue("ai:imageExpiration.noImageUsageExpirationDate"));*/
        //Model Usage Right
        photoDoc.setPropertyValue("cur:modelName",parentDoc.getPropertyValue("cur:modelName"));
        photoDoc.setPropertyValue("cur:modelAgency",parentDoc.getPropertyValue("cur:modelAgency"));
        photoDoc.setPropertyValue("cur:modelMediaUsageRights",parentDoc.getPropertyValue("cur:modelMediaUsageRights"));
        photoDoc.setPropertyValue("cur:credits",parentDoc.getPropertyValue("cur:credits"));
        photoDoc.setPropertyValue("cur:modelExpiration",parentDoc.getPropertyValue("cur:modelExpiration"));
        /*
        photoDoc.setPropertyValue("cur:modelExpiration.modelUsageRight",parentDoc.getPropertyValue("cur:modelExpiration.modelUsageRight"));
        photoDoc.setPropertyValue("cur:modelExpiration.modelMediaUsageExpirationDateEnd",parentDoc.getPropertyValue("cur:modelExpiration.modelMediaUsageExpirationDateEnd"));
        photoDoc.setPropertyValue("cur:modelExpiration.noModelMediaUsageExpirationDate",parentDoc.getPropertyValue("cur:modelExpiration.noModelMediaUsageExpirationDate"));*/

        //Photographer Usage Right
        photoDoc.setPropertyValue("cur:photographerName",parentDoc.getPropertyValue("cur:photographerName"));
        photoDoc.setPropertyValue("cur:photographerMediaUsageRights",parentDoc.getPropertyValue("cur:photographerMediaUsageRights"));
        photoDoc.setPropertyValue("cur:photographerExpiration",parentDoc.getPropertyValue("cur:photographerExpiration"));
        /*
        photoDoc.setPropertyValue("cur:photographerExpiration.photographerUsageRight",parentDoc.getPropertyValue("cur:photographerExpiration.photographerUsageRight"));
        photoDoc.setPropertyValue("cur:photographerExpiration.photographerMediaUsageExpirationDateEnd",parentDoc.getPropertyValue("cur:photographerExpiration.photographerMediaUsageExpirationDateEnd"));
        photoDoc.setPropertyValue("cur:photographerExpiration.noPhotographerlMediaUsageExpirationDate",parentDoc.getPropertyValue("cur:photographerExpiration.noPhotographerlMediaUsageExpirationDate"));*/

        //Stock Usage right
        photoDoc.setPropertyValue("cur:stockImageExpiration",parentDoc.getPropertyValue("cur:stockImageExpiration"));
        /*
        photoDoc.setPropertyValue("cur:stockImageExpiration.stockImageUsageRight",parentDoc.getPropertyValue("cur:stockImageExpiration.stockImageUsageRight"));
        photoDoc.setPropertyValue("cur:stockImageExpiration.stockImageUsageExpirationDateEnd",parentDoc.getPropertyValue("cur:stockImageExpiration.stockImageUsageExpirationDateEnd"));
        photoDoc.setPropertyValue("cur:stockImageExpiration.noStockImageUsageExpirationDate",parentDoc.getPropertyValue("cur:stockImageExpiration.noStockImageUsageExpirationDate"));*/

parentDoc.getProperty("ds").isD
        return photoDoc;
    }
}