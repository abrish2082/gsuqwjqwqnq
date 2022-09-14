/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.LuCurrencyBeanLocal;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author mora
 */
@Named(value = "LuCurrencyController")
@ViewScoped
public class LuCurrencyController implements Serializable {

            @Inject
            FmsLuCurrency currency;
            @EJB
            LuCurrencyBeanLocal luCurrencyBeanLocal;

            /**
             * Creates a new instance of LuCurrencyController
             */
            public LuCurrencyController() {
            }
            int updateStatus = 0;
            String saveUpdate = "Save";
            private boolean disableBtnCreate;
            private String createOrSearchBundle = "New";
            private boolean renderPnlCreateAdditional = false;
            private boolean renderPnlManPage = true;
            private String icone = "ui-icon-document";
            private String activeIndex;
            boolean disable = false;

                //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
            public FmsLuCurrency getCurrency() {
                        if (currency == null) {
                                    currency = new FmsLuCurrency();
                        }
                        return currency;
            }

            public void setCurrency(FmsLuCurrency currency) {
                        this.currency = currency;
            }

            private void saveUpdateClear() {
                        currency = null;

            }

            public boolean isDisableBtnCreate() {
                        return disableBtnCreate;
            }

            public void setDisableBtnCreate(boolean disableBtnCreate) {
                        this.disableBtnCreate = disableBtnCreate;
            }

            public String getCreateOrSearchBundle() {
                        return createOrSearchBundle;
            }

            public void setCreateOrSearchBundle(String createOrSearchBundle) {
                        this.createOrSearchBundle = createOrSearchBundle;
            }

            public boolean isRenderPnlCreateAdditional() {
                        return renderPnlCreateAdditional;
            }

            public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
                        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
            }

            public boolean isRenderPnlManPage() {
                        return renderPnlManPage;
            }

            public void setRenderPnlManPage(boolean renderPnlManPage) {
                        this.renderPnlManPage = renderPnlManPage;
            }

            public String getIcone() {
                        return icone;
            }

            public void setIcone(String icone) {
                        this.icone = icone;
            }

            public String getActiveIndex() {
                        return activeIndex;
            }

            public void setActiveIndex(String activeIndex) {
                        this.activeIndex = activeIndex;
            }

            public boolean isDisable() {
                        return disable;
            }

            public void setDisable(boolean disable) {
                        this.disable = disable;
            }

            public int getUpdateStatus() {
                        return updateStatus;
            }

            public void setUpdateStatus(int updateStatus) {
                        this.updateStatus = updateStatus;
            }

            public String getSaveUpdate() {
                        return saveUpdate;
            }

            public void setSaveUpdate(String saveUpdate) {
                        this.saveUpdate = saveUpdate;
            }
    //</editor-fold> 

            public String save() {

                        if (updateStatus == 1) {
                                    luCurrencyBeanLocal.create(null);
                                    JsfUtil.addSuccessMessage("operating Budget successfully register");
                        } else {
                                    luCurrencyBeanLocal.edit(currency);
                                    JsfUtil.addSuccessMessage("operating Budget info is Edited");
                                    saveUpdate = "Create";
                        }
                        saveUpdateClear();
                        return null;
            }

            public ArrayList<FmsLuCurrency> searchType(String name) {
                        ArrayList<FmsLuCurrency> currencys = null;
                        currency.setCountry(name);
                        currencys = luCurrencyBeanLocal.searchCurrencys(currency);
                        return currencys;
            }

            public void getImportationInfo(SelectEvent event) {
                        currency.setCurrency(event.getObject().toString());
                        currency = luCurrencyBeanLocal.getCurrencyinfo(currency);
                        System.out.println("bondTypevv++++++++++++++++++ " + currency.getCurrency());
                        updateStatus = 2;
                        saveUpdate = "Update";
                        createOrSearchBundle = "Search";
                        createNewAdditionalAmount();

            }

            public void createNewAdditionalAmount() {
                        System.out.println("=========================create gebtual============");
                        saveUpdateClear();
                        disableBtnCreate = false;
                        if (createOrSearchBundle.equals("New")) {
                                    renderPnlCreateAdditional = true;
                                    renderPnlManPage = false;
                                    createOrSearchBundle = "Search";
                                    setIcone("ui-icon-search");
                        } else if (createOrSearchBundle.equals("Search")) {
                                    renderPnlCreateAdditional = false;
                                    renderPnlManPage = true;
                                    createOrSearchBundle = "New";
                                    setIcone("ui-icon-document");
                        }
            }

}
