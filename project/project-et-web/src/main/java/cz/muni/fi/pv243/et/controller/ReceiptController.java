package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.PersonListProducer;
import cz.muni.fi.pv243.et.data.ReceiptListProducer;
import cz.muni.fi.pv243.et.data.ReceiptRepository;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.Receipt;
import cz.muni.fi.pv243.et.util.LongHolder;
import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.picketlink.Identity;
import org.picketlink.idm.model.Attribute;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Model
public class ReceiptController {

    @Inject
    private ReceiptListProducer receiptListProducer;

    @Inject
    private ReceiptRepository receiptRepository;

    @Inject
    private ReceiptModel receiptModel;

    private UploadedFile uploadedFile;

    @Inject
    private Identity identity;

    @Inject
    private FacesContext facesContext;

    @Inject
    private PersonListProducer personListProducer;

    @Produces
    @Named("allReceipts")
    public Collection<Receipt> getAllReceipts() {
        return receiptListProducer.getAllReceipts();
    }

    public String saveReceipt() throws IOException {
        Receipt r = receiptModel.getReceipt();

        String fileName = FilenameUtils.getName(uploadedFile.getName());
        String contentType = uploadedFile.getContentType();
        byte[] bytes = uploadedFile.getBytes();

        r.setDocumentName(fileName);
        r.setDocument(bytes);

        if (r.getId() == null) {
            r.setImportDate(new Date());
            r.setImportedBy(getCurrentPerson());
            receiptRepository.create(r);
        } else {
            receiptRepository.update(r);
        }

        return "receipts?faces-redirect=true";
    }

    public String editReceipt(Long id) {
        Receipt r = receiptListProducer.getReceipt(id);
        receiptModel.setReceipt(r);

        return "editReceipt";
    }

    public String createReceipt() {
        receiptModel.setReceipt(new Receipt());

        return "createReceipt";
    }

    public String removeReceipt(Long id) {
        receiptModel.setReceipt(null);

        receiptRepository.remove(receiptListProducer.getReceipt(id));

        return "receipts?faces-redirect=true";
    }

    public void showFile(Long receiptId) throws IOException {
        Receipt r = receiptListProducer.getReceipt(receiptId);

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        ec.setResponseContentType(ec.getMimeType(r.getDocumentName())); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
        ec.setResponseContentLength(r.getDocument().length); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + r.getDocumentName() + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

        OutputStream stream = ec.getResponseOutputStream();
        stream.write(r.getDocument());

        fc.responseComplete();
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    private Person getCurrentPerson() {
        return identity.getUser().<Person>getAttribute("person").getValue();
    }
}
