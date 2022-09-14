/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.utility;


import java.io.Serializable;

/**
 *
 * @author mora1
 */
public class Dictionary implements Serializable{

    private static int INACTIVE = 0;
    private static int ACTIVE = 1;
    private static int PREPARED = 2;
    private static int UPDATED = 3;
    private static int APPROVED = 4;
    private static int REJECTED = 5;
    private static int ACCEPTED = 6;
    private static int NOTACCEPTED = 7;
    private static int AUTHORIZED = 8;
    private static int NOTAUTHORIZED = 9;
    private static int ISSUED = 10;
    private static int NOTISSUED = 11;
    private static int FINALIZED = 12;
    private static int VOID = 13;
    private static int AMENDED = 14;
    private static int CHECKED = 15;
    private static int NOTCHECKED = 16;
    private static int REQUESTED = 17;
    private static int NOTPREPARED = 18;
    private static int NOTAPPROVED = 18;    

    /**
     * @return the CHECKED
     */
    public static int getCHECKED() {
        return CHECKED;
    }

    /**
     * @param aCHECKED the CHECKED to set
     */
    public static void setCHECKED(int aCHECKED) {
        CHECKED = aCHECKED;
    }

    /**
     * @return the NOTCHECKED
     */
    public static int getNOTCHECKED() {
        return NOTCHECKED;
    }

    /**
     * @param aNOTCHECKED the NOTCHECKED to set
     */
    public static void setNOTCHECKED(int aNOTCHECKED) {
        NOTCHECKED = aNOTCHECKED;
    }

    public Dictionary() {
    }

    public static int getINACTIVE() {
        return INACTIVE;
    }

    public static void setINACTIVE(int INACTIVE) {
        Dictionary.INACTIVE = INACTIVE;
    }

    public static int getACTIVE() {
        return ACTIVE;
    }

    public static void setACTIVE(int ACTIVE) {
        Dictionary.ACTIVE = ACTIVE;
    }

    public static int getPREPARED() {
        return PREPARED;
    }

    public static void setPREPARED(int PREPARED) {
        Dictionary.PREPARED = PREPARED;
    }

    public static int getUPDATED() {
        return UPDATED;
    }

    public static void setUPDATED(int UPDATED) {
        Dictionary.UPDATED = UPDATED;
    }

    public static int getAPPROVED() {
        return APPROVED;
    }

    public static void setAPPROVED(int APPROVED) {
        Dictionary.APPROVED = APPROVED;
    }

    public static int getREJECTED() {
        return REJECTED;
    }

    public static void setREJECTED(int REJECTED) {
        Dictionary.REJECTED = REJECTED;
    }

    public static int getACCEPTED() {
        return ACCEPTED;
    }

    public static void setACCEPTED(int ACCEPTED) {
        Dictionary.ACCEPTED = ACCEPTED;
    }

    public static int getNOTACCEPTED() {
        return NOTACCEPTED;
    }

    public static void setNOTACCEPTED(int NOTACCEPTED) {
        Dictionary.NOTACCEPTED = NOTACCEPTED;
    }

    public static int getAUTHORIZED() {
        return AUTHORIZED;
    }

    public static void setAUTHORIZED(int AUTHORIZED) {
        Dictionary.AUTHORIZED = AUTHORIZED;
    }

    public static int getNOTAUTHORIZED() {
        return NOTAUTHORIZED;
    }

    public static void setNOTAUTHORIZED(int NOTAUTHORIZED) {
        Dictionary.NOTAUTHORIZED = NOTAUTHORIZED;
    }

    public static int getISSUED() {
        return ISSUED;
    }

    public static void setISSUED(int ISSUED) {
        Dictionary.ISSUED = ISSUED;
    }

    public static int getNOTISSUED() {
        return NOTISSUED;
    }

    public static void setNOTISSUED(int NOTISSUED) {
        Dictionary.NOTISSUED = NOTISSUED;
    }

    public static int getFINALIZED() {
        return FINALIZED;
    }

    public static void setFINALIZED(int FINALIZED) {
        Dictionary.FINALIZED = FINALIZED;
    }

    public static int getVOID() {
        return VOID;
    }

    public static void setVOID(int VOID) {
        Dictionary.VOID = VOID;
    }

    public static int getAMENDED() {
        return AMENDED;
    }

    public static void setAMENDED(int AMENDED) {
        Dictionary.AMENDED = AMENDED;
    }

    public static int getREQUESTED() {
        return REQUESTED;
    }

    public static void setREQUESTED(int REQUESTED) {
        Dictionary.REQUESTED = REQUESTED;
    }

    public static int getNOTPREPARED() {
        return NOTPREPARED;
    }

    public static void setNOTPREPARED(int NOTPREPARED) {
        Dictionary.NOTPREPARED = NOTPREPARED;
    }

    public static int getNOTAPPROVED() {
        return NOTAPPROVED;
    }

    public static void setNOTAPPROVED(int NOTAPPROVED) {
        Dictionary.NOTAPPROVED = NOTAPPROVED;
    }    
    
}
