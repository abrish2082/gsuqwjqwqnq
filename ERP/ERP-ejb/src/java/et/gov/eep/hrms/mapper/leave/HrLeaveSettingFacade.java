/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.leave;

import et.gov.eep.hrms.entity.leave.HrLeaveSetting;
import et.gov.eep.hrms.entity.leave.HrLuLeaveTypes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author prg
 */
@Stateless
public class HrLeaveSettingFacade extends AbstractFacade<HrLeaveSetting> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLeaveSettingFacade() {
        super(HrLeaveSetting.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Bussiness Immplementation">
    public HrLeaveSetting findLeaveTypeByCode(HrLeaveSetting leaveSetting) {
        Query query = em.createNamedQuery("HrLeaveSetting.findByLeaveTypeCode");
        query.setParameter("leaveTypeCodes", leaveSetting.getLeaveTypeCode());
        try {
            HrLeaveSetting req = (HrLeaveSetting) query.getResultList().get(0);
            return req;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public HrLeaveSetting findLeaveSettingByLeaveType(HrLuLeaveTypes luLeaveTypes) {
        Query query = em.createNamedQuery("HrLeaveSetting.findByLeaveTypeId");
        query.setParameter("leaveTypeId", luLeaveTypes.getId());
        try {
            if (query.getResultList().size() > 0) {
                HrLeaveSetting setting = (HrLeaveSetting) query.getResultList().get(0);
                return setting;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<HrLeaveSetting> searchLeaveTypeByCode(HrLeaveSetting leaveSetting) {
        Query query = em.createNamedQuery("HrLeaveSetting.findLikeLeaveTypeCode");
        query.setParameter("leaveTypeCode", leaveSetting.getLeaveTypeCode() + '%');
        try {
            ArrayList<HrLeaveSetting> req = new ArrayList(query.getResultList());
            return req;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<HrLeaveSetting> filterByGender(String gn) {
        Query query = em.createNamedQuery("HrLeaveSetting.findLikeGender");
        query.setParameter("gender", gn);
        try {
            ArrayList<HrLeaveSetting> req = new ArrayList(query.getResultList());
            return req;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static Integer getDuration(int month, int day, int year) throws Exception {
        /* input calendar date */
        month--; // following the 0-based rule
        Calendar cal = new GregorianCalendar(year, month, day);
        
        /* date today */
        java.util.Date today = new java.util.Date();
        
        /* year now */
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        int intYear = Integer.parseInt(sdfYear.format(today));
        
        /* month now */
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
        int intMonth = Integer.parseInt(sdfMonth.format(today));
        intMonth--; // following the 0-based rule
        
        /* day now */
        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
        int intDay = Integer.parseInt(sdfDay.format(today));
        
        /* calendar date now */
        Calendar now = new GregorianCalendar(intYear, intMonth, intDay);
        
        /* years duration */
        int yyyy = intYear - year;
        
        /* array of days in 12 months */
        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        /*
        * an indicator if given date has already occurred. (-1) - if given date
        * is greater than the current date; 0 - if given date has not yet
        * occurred or if it's equal to the current date.
        */
        int factor = 0;
        
        int mm = 0; // month duration
        
        int dd = 0; // day duration
        
        /* determine if given date is greater than or equal to the current date */
        if ((month > intMonth) || (month == intMonth && day > intDay)) {
            factor = -1;
            yyyy += factor;
        }
        
        /* throw if any of the following exceptions occur */
        if (month > 12) {
            throw new Exception("Invalid input month");
        } else if (day > months[month]) {
            throw new Exception("Invalid input day");
        } else if (yyyy < 0) {
            throw new Exception("Invalid input date");
        }
        
        /*
        * if the given date has already passed or if it's equal to the current
        * date
        */
        if (factor == 0) {
            // compute for days in between the given and the current date
            dd = now.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
            /* determine if the current year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
            * compute for day duration and month duration between the given
            * date and the given day of the current month
            */
            for (int i = month; i <= intMonth; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            /*
            * if computed month duration is more than 12, finalize values for
            * year and month duration
            */
            if (mm >= 12) {
                yyyy += (mm / 12);
                mm %= 12;
            }
        } else { // if the given date is greater than the current date
            intYear--; // derive previous year
            /* set Calendar date for December 31 of the previous year */
            Calendar prev = new GregorianCalendar(intYear, 11, 31);
            /*
            * compute the days in between the given date last year and the
            * current date
            */
            dd = (prev.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR)) + now.get(Calendar.DAY_OF_YEAR);
            /* determine if previous year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
            * compute for day duration and month duration between the given
            * date and the given day in December of the previous year
            */
            for (int i = month; i <= 11; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            intYear++; // set the value back to the current year
            /* determine if current year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
            * compute for day duration and month duration between the given day
            * in January of the current year and the current date
            */
            for (int i = 0; i <= intMonth; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            /*
            * if computed month duration is more than 12, finalize values for
            * year and month duration
            */
            if (mm >= 12) {
                yyyy += (mm / 12);
                mm %= 12;
            }
        }
        
        /* computed duration in years, months and days */
        //return yyyy + " Year(s), " + mm + " Month(s), and " + dd + " Day(s) ";
        return yyyy;
    }
    
    public HrLeaveSetting getByLeaveType(HrLuLeaveTypes hrLuLeaveTypes) {
        try {
            Query query = em.createNamedQuery("HrLeaveSetting.findByLeaveType", HrLeaveSetting.class);
            query.setParameter("hrLuLeaveTypes", hrLuLeaveTypes);
            return (HrLeaveSetting) query.getSingleResult();
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
    }
    
//</editor-fold>

}
