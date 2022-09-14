package securityBean;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import et.gov.eep.commonApplications.entity.Status;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import webService.UserStatus;

/**
 *
 * @author Hilwa
 */
@Named("workFlow")
@ViewScoped
public class WorkFlow implements Serializable {

    Set<String> permissionValues = new HashSet<String>();
    int userAccount;
    String userName;
    private boolean prepareStatus = false;
    private boolean approveStatus = false;
    private boolean checkStatus = false;
    private boolean authorizeStatus = false;
    private boolean rejected = false;
    private String preparedBy;
    private String approvedBy;
    private String checkedBy;
    private boolean buttonStatus = false; //to disable and enable save or update button

    private boolean readonly = false;

    private String btnBundle;

    private int userStatus;

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getBtnBundle() {
        return btnBundle;
    }

    public void setBtnBundle(String btnBundle) {
        this.btnBundle = btnBundle;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public boolean isButtonStatus() {
        return buttonStatus;
    }

    public void setButtonStatus(boolean buttonStatus) {
        this.buttonStatus = buttonStatus;
    }

    public boolean isPrepareStatus() {
        return prepareStatus;
    }

    public void setPrepareStatus(boolean prepareStatus) {
        this.prepareStatus = prepareStatus;
    }

    public boolean isApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(boolean approveStatus) {
        this.approveStatus = approveStatus;
    }

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    public boolean isAuthorizeStatus() {
        return authorizeStatus;
    }

    public void setAuthorizeStatus(boolean authorizeStatus) {
        this.authorizeStatus = authorizeStatus;
    }

    public int getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(int userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    Status status = new Status();

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }
    @Inject
    SessionBean sessionBeanLocal;

    public WorkFlow() {

    }

    @PostConstruct
    public void _init() {
        permissionValues.clear();
        prepareStatus = false;
        approveStatus = false;
        checkStatus = false;
        authorizeStatus = false;
        status = new Status();
        userName = sessionBeanLocal.getUserName();
        userAccount = sessionBeanLocal.getUserId();
//        sessionBean.employeeInformation();
//        sessionBean.getPermission();
        sessionBeanLocal.getPermission();
        UserStatus permissionValues = sessionBeanLocal.getPermissions();
        if (permissionValues != null) {
            if (permissionValues.isApprove()
                    && permissionValues.isPrepare()) {

                userStatus = Constants.APPROVE_VALUE;
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.PREPARE_VALUE);
                btnBundle = "Approve";
                buttonStatus = true;
                prepareStatus = true;
                approveStatus = true;
                approvedBy = userName;
                preparedBy = userName;
                readonly = false;

            } else if (permissionValues.isApprove()) {
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.PREPARE_VALUE);
                status.setStatus3(Constants.CHECK_APPROVE_VALUE);
                userStatus = Constants.APPROVE_VALUE;
                btnBundle = "Approve";
                buttonStatus = false;
                approveStatus = true;
                approvedBy = userName;
                readonly = true;

            } else if (permissionValues.isPrepare()) {

                status.setStatus1(Constants.PREPARE_VALUE);
                status.setStatus2(Constants.CHECK_REJECT_VALUE);
//                status.setStatus3(Constants.APPROVE_REJECT_VALUE);
                userStatus = Constants.PREPARE_VALUE;
                btnBundle = "Prepare";
                buttonStatus = true;
                prepareStatus = true;
                preparedBy = userName;
                readonly = false;

            } else if (permissionValues.isChecker()) {

                status.setStatus1(Constants.CHECK_APPROVE_VALUE);
                status.setStatus2(Constants.PREPARE_VALUE);
                userStatus = Constants.CHECK_APPROVE_VALUE;
                btnBundle = "Check";
                buttonStatus = false;
                checkStatus = true;
                checkedBy = userName;
                readonly = true;
            } else if (permissionValues.isAuthorize()) {
                status.setStatus1(Constants.AUTHORIZED);
                status.setStatus1(Constants.APPROVE_VALUE);
                status.setStatus2(Constants.PREPARE_VALUE);
                status.setStatus3(Constants.CHECK_APPROVE_VALUE);
                userStatus = Constants.AUTHORIZED;
                btnBundle = "Authorize";
                buttonStatus = false;
                authorizeStatus = true;
                checkedBy = userName;
                readonly = true;
            }

        }
    }

    /**
     * @return the readonly
     */
    public boolean isReadonly() {
        return readonly;
    }

    /**
     * @param readonly the readonly to set
     */
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

}
