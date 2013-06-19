package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.PersonListProducer;
import cz.muni.fi.pv243.et.data.ReceiptListProducer;
import cz.muni.fi.pv243.et.data.ReceiptRepository;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.PersonWrapper;
import cz.muni.fi.pv243.et.model.Receipt;
import cz.muni.fi.pv243.et.security.IdentityHelper;
import cz.muni.fi.pv243.et.service.ReceiptService;
import cz.muni.fi.pv243.et.util.CurrentPerson;
import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.picketlink.Identity;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;

@Model
public class ReceiptController {

    @Inject
    private ReceiptService receiptService;

    @Inject
    private ReceiptModel receiptModel;

    @NotNull
    private UploadedFile uploadedFile;

    @Inject
    private Identity identity;

    @Inject
    private FacesContext facesContext;

    @Inject
    @CurrentPerson
    private PersonWrapper currentPerson;

    @Produces
    @Named("allReceipts")
    public Collection<Receipt> getAllReceipts() {
        return receiptService.findAll();
    }

    @Produces
    @Named("currentUserReceipts")
    public Collection<Receipt> getCurrentUserReceipts() {
        return receiptService.findForPerson(currentPerson.getPerson());
    }

    public String saveReceipt() throws IOException {
        Receipt r = null;
        Long id = receiptModel.getReceiptId();
        if (id == null) {
            r = new Receipt();
        } else {
            r = receiptService.get(id);
        }


        String fileName = FilenameUtils.getName(uploadedFile.getName());
        byte[] bytes = uploadedFile.getBytes();

        r.setDocumentName(fileName);
        r.setDocument(bytes);

        if (r.getId() == null) {
            r.setImportDate(new Date());
            r.setImportedBy(currentPerson.getPerson());
        }
        receiptService.save(r);

//        String url = FacesContext.getCurrentInstance().getViewRoot().getViewId();
//        return "/secured/receipts?faces-redirect=true&backurl=" + url;
//        return "/secured/receipts?faces-redirect=true";
//        System.out.println("saveReceipt() =" + receiptModel.getBackUrl());
        return receiptModel.getBackUrl();
    }

    public String editReceipt(Long id) {
        receiptModel.setReceiptId(id);

        return "/secured/editReceipt";
    }

    public String createReceipt(String fromUrl) {
        receiptModel.setReceiptId(null);
        receiptModel.setBackUrl(fromUrl);

        return "/secured/createReceipt";
    }

    public String removeReceipt(Long id) {
        receiptModel.setReceiptId(null);

        receiptService.remove(receiptService.get(id));

        return "/secured/receipts?faces-redirect=true";
    }

    public String cancel() {
        System.out.println("cancel=" + receiptModel.getBackUrl());
        return receiptModel.getBackUrl();
    }

    public void showFile(Long receiptId) throws IOException {
        Receipt r = receiptService.get(receiptId);

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.responseReset();
        ec.setResponseContentType(ec.getMimeType(r.getDocumentName()));
        ec.setResponseContentLength(r.getDocument().length);
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + r.getDocumentName() + "\"");

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

}
