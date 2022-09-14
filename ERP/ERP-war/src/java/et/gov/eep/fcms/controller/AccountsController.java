/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.AccountsBeanLocal;
import et.gov.eep.fcms.entity.FmsAccounts;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author mora1
 */
@Named(value = "accountsController")
@ViewScoped
public class AccountsController implements Serializable {

    /**
     * Creates a new instance of AccountsController
     */
    public AccountsController() {
    }

    @Inject
    FmsAccounts fmsAccounts;
    @EJB
    AccountsBeanLocal accountsBeanLocal;
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    private String SaveOrUpdateButton = "Save";
    private String createOrSearchBundle = "New";
    private String headerTitle = "Chart of Account Search";
    private String icone = "ui-icon-document";
    private boolean disableBtnCreate;
    private boolean disableParent=false;
    private boolean disableBtnSubsdery = false;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private Boolean update = false;
    private List<FmsAccounts> fmsAccountsesall;
    List<String> ParentId;

    @PostConstruct
    public void init() {
        fmsAccountsesall = accountsBeanLocal.findAll();
        root = new DefaultTreeNode("Root", null);
        String id;
        id="0";
        recursive(fmsAccountsesall,id, root);
        root2 = getRoot();
    }
    private static List<FmsAccounts> araListe;

    public void recursive(List<FmsAccounts> liste, String id, TreeNode node) {
        araListe = new ArrayList<>();
        if (fmsAccountsesall != null) {
            for (FmsAccounts k : fmsAccountsesall) {
                if (k.getParentId() != null && (k.getParentId() == null ? id == null : k.getParentId().equals(id))) {
                    TreeNode childNode = new DefaultTreeNode(k.getAccountsCode()+ "-" + k.getAccountsTitle(), node);
                    araListe.add(k);
                    recursive(araListe, k.getAccountsCode(), childNode);
                }
            }
        }
    }

//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public FmsAccounts getFmsAccounts() {
        return fmsAccounts;
    }

    public void setFmsAccounts(FmsAccounts fmsAccounts) {
        this.fmsAccounts = fmsAccounts;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isDisableBtnSubsdery() {
        return disableBtnSubsdery;
    }

    public void setDisableBtnSubsdery(boolean disableBtnSubsdery) {
        this.disableBtnSubsdery = disableBtnSubsdery;
    }

    public boolean isDisableParent() {
        return disableParent;
    }

    public void setDisableParent(boolean disableParent) {
        this.disableParent = disableParent;
    }

    public static List<FmsAccounts> getAraListe() {
        return araListe;
    }

    public static void setAraListe(List<FmsAccounts> araListe) {
        AccountsController.araListe = araListe;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public List<FmsAccounts> getFmsAccountsesall() {
        return fmsAccountsesall;
    }

    public void setFmsAccountsesall(List<FmsAccounts> fmsAccountsesall) {
        this.fmsAccountsesall = accountsBeanLocal.findAll();
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getRoot2() {
        return root2;
    }

    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }

    public TreeNode getAccountRoot() {
        return accountRoot;
    }

    public void setAccountRoot(TreeNode accountRoot) {
        this.accountRoot = accountRoot;
    }

    public TreeNode getAccountSelectionNode() {
        return accountSelectionNode;
    }

    public void setAccountSelectionNode(TreeNode accountSelectionNode) {
        this.accountSelectionNode = accountSelectionNode;
    }

    public static List<FmsAccounts> getAllAccountses() {
        return allAccountses;
    }

    public static void setAllAccountses(List<FmsAccounts> allAccountses) {
        AccountsController.allAccountses = allAccountses;
    }

    public static List<FmsAccounts> getAccountses() {
        return accountses;
    }

    public static void setAccountses(List<FmsAccounts> accountses) {
        AccountsController.accountses = accountses;
    }

    public String getAllAccountUnitAsOne() {
        return allAccountUnitAsOne;
    }

    public void setAllAccountUnitAsOne(String allAccountUnitAsOne) {
        this.allAccountUnitAsOne = allAccountUnitAsOne;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public List<String> getParentId() {
        return ParentId;
    }

    public void setParentId(List<String> ParentId) {
        this.ParentId = ParentId;
    }

    //</editor-fold> 
    public SelectItem[] getListAccounts() {
        return JsfUtil.getSelectItems(accountsBeanLocal.findAll(), true);
    }

    public void create() {
        if (update == false) {
            accountsBeanLocal.create(fmsAccounts);
            clear();
        } else {
            accountsBeanLocal.edit(fmsAccounts);
            clear();
        }
    }

    public List<FmsAccounts> fmsAccountses(FmsAccounts subsidiary) {
        return accountsBeanLocal.fmsAccountses(subsidiary);
    }

    public FmsAccounts fmsAccountsDitle(FmsAccounts fmsAccounts) {
        return accountsBeanLocal.fmsAccountsDitle(fmsAccounts);
    }

    public void createNewView() {
        disableBtnCreate = false;
        switch (createOrSearchBundle) {
            case "New":
                renderPnlCreateGatePass = true;
                renderPnlManPage = false;
                createOrSearchBundle = "Search";
                headerTitle = "Chart of Account Rgistration";
                setIcone("ui-icon-search");
                break;
            case "Search":
                renderPnlCreateGatePass = false;
                renderPnlManPage = true;
                createOrSearchBundle = "New";
                headerTitle = "Chart of Account Search";
                setIcone("ui-icon-document");
                break;
        }
    }

    //  <editor-fold defaultstate="collapsed" desc=" Getter & Setters ">
    public TreeNode getAddressRoot() {
        return accountRoot;
    }

    public void setAddressRoot(TreeNode accountRoot) {
        this.accountRoot = accountRoot;
    }

    public TreeNode getAddressSelectedNode() {
        return accountSelectionNode;
    }

    public void setAddressSelectedNode(TreeNode accountSelectionNode) {
        this.accountSelectionNode = accountSelectionNode;
    }

   //  </editor-fold>   
    //  <editor-fold defaultstate="collapsed" desc="Address Tree">
    private TreeNode accountRoot;
    private TreeNode accountSelectionNode;
    private static List<FmsAccounts> allAccountses;
    private static List<FmsAccounts> accountses;
    private String accountCode;
    private String allAccountUnitAsOne;

    public void loadAccountsTree() {
        allAccountses = accountsBeanLocal.findAll();
        accountRoot = new DefaultTreeNode("Root", null);
        String parent = "0";
        populateAccountsNode(allAccountses, parent, accountRoot);
    }

    public void populateAccountsNode(List<FmsAccounts> addressData, String id, TreeNode node) {
        accountses = new ArrayList<>();
        for (FmsAccounts k : getAllAccountses()) {
            if (k.getParentId() == id) {
                TreeNode childNode = new DefaultTreeNode(k.getAccountsCode()+ "=>" + k.getAccountsTitle(), node);
                accountses.add(k);
                populateAccountsNode(accountses, k.getAccountsCode(), childNode);
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        accountCode = selectedNode.getData().toString().split("-")[0];
        fmsAccounts.setAccountsCode(accountCode);
        fmsAccounts = accountsBeanLocal.fmsAccountsByCode(fmsAccounts);
        System.out.println("morat men " + fmsAccounts.getAccountsCode());
    }

    public void onNodeSelect_sub(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
         accountCode = selectedNode.getData().toString().split("-")[0];
        fmsAccounts.setAccountsCode(accountCode);
        fmsAccounts = accountsBeanLocal.fmsAccountsByCode(fmsAccounts);
        System.out.println("morat men " + fmsAccounts.getAccountsCode());
        createNewView();
        SaveOrUpdateButton = "Update";
        disableBtnSubsdery=true;

    }

    //  </editor-fold>

    public void clear() {
        selectedNode = null;
        fmsAccounts = null;
        disableBtnCreate=false;
        createNewView();
    }
public void SubsidiaryLedger()
{String parent="0";
if(fmsAccounts!=null)
{
  parent=fmsAccounts.getAccountsCode();
  
}
    fmsAccounts=new FmsAccounts();
    fmsAccounts.setParentId(parent);
    fmsAccounts.setAccountsCode("");
    fmsAccounts.setAccountsTitle("");
    disableBtnSubsdery=false;
    disableParent=true;
    System.out.println("parent = "+parent); 
    SaveOrUpdateButton = "Save";
        update=false;
}
    public SelectItem[] ParentIdList() {
        fmsAccountsesall = accountsBeanLocal.findAll();

        ParentId = new ArrayList<>();
        if (fmsAccountsesall.size() > 0) {

            for (int i = 0; i < fmsAccountsesall.size(); i++) {
                getParentId().add(fmsAccountsesall.get(i).getAccountsCode());
            }
        }
        SelectItem[] listSl = null;
        if (ParentId.size() > 0) {
            listSl = new SelectItem[ParentId.size() + 1];
            listSl[0] = new SelectItem(null, "--- Select One ---");
            for (int i = 0; i < ParentId.size(); i++) {
                listSl[i + 1] = new SelectItem(ParentId.get(i));
            }
        }
        return listSl;
    }
}
