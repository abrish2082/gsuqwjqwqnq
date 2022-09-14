
package et.gov.eep.hrms.businessLogic.documentProvidingService;

import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.documentProvidingService.HrDocumentRequests;
import et.gov.eep.hrms.mapper.documentProvidingService.HrDocumentRequestsFacade;
import et.gov.eep.hrms.mapper.documentProvidingService.HrDocumentTypesFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;


@Stateless
public class DocumentProvidingServiceBean implements DocumentProvidingServiceBeanLocal {

    @EJB
    HrDocumentTypesFacade hrDocumentTypesFacade;
    @EJB
    HrDocumentRequestsFacade hrDocumentRequestsFacade;

    @Override
    public void save(HrDocumentRequests hrDocumentRequests) {
        hrDocumentRequestsFacade.create(hrDocumentRequests);
    }

    @Override
    public void update(HrDocumentRequests hrDocumentRequests) {
        hrDocumentRequestsFacade.edit(hrDocumentRequests);
    }
    
    @Override
    public List<HrDocumentRequests> findAllrequests() {
        return hrDocumentRequestsFacade.findAllRequests();
    }

    @Override
    public HrDocumentRequests loadDocumentRequestDetails(BigDecimal id) {
        return hrDocumentRequestsFacade.loadDocumentrequestDetails(id);
    }

    @Override
    public List<HrDocumentRequests> findAllAprovedrequests(Integer status) {
        return hrDocumentRequestsFacade.findAllAprovedrequests(status);
    }

    @Override
    public boolean searchByEmpIdAndDocType(HrDocumentRequests hrDocumentRequests) {
        return hrDocumentRequestsFacade.searchByEmpIdAndDocType(hrDocumentRequests);
    }

    @Override
    public String searchreqdatebyEmpId(HrDocumentRequests hrDocumentRequests) {
        return hrDocumentRequestsFacade.searchreqdatebyEmpId(hrDocumentRequests);
    }
    @Override
    public List<HrDocumentRequests> findAllAprovedrequests() {
        return hrDocumentRequestsFacade.findAllApprovedRequests();
    }

    @Override
    public List<HrDocumentRequests> findAll() {
        return hrDocumentRequestsFacade.findAll();
    }

    @Override
    public Integer searchByEmpIdAndDocType1(HrDocumentRequests hrDocumentRequests) {
        return hrDocumentRequestsFacade.searchByEmpIdAndDocType1(hrDocumentRequests);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("-1"), "Load Rejected List"));
        selectItems.add(new SelectItem(String.valueOf("-3"), "Load Generated List"));
        return selectItems;
    }

    @Override
    public List<SelectItem> filterByStatus1() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
         selectItems.add(new SelectItem(String.valueOf("3"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("-1"), "Load Rejected List"));
        selectItems.add(new SelectItem(String.valueOf("-3"), "Load Generated List"));
        return selectItems;
    }

    @Override
    public List<HrDocumentRequests> loadDocumentRequests(int status) {
        return hrDocumentRequestsFacade.loadDocumentRequests(status);
    }

    @Override
    public List<SelectItem> filterByStatus2() {
         List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("-3"), "Load Generated List"));
        return selectItems;
    }

    @Override
    public List<HrAddresses> findAllAddress() {
       return hrDocumentRequestsFacade.findalladresses();
    }

    @Override
    public HrAddresses findAllAddressUnitAsOne(HrAddresses hrAddresses) {
       return hrDocumentRequestsFacade.findadress(hrAddresses);
    }
     @Override
    public List<HrDocumentRequests> loadDocumentRequests(int status, int UserId) {
         return hrDocumentRequestsFacade.loadDocumentRequestsAndUserID(status,UserId);
    }

}
