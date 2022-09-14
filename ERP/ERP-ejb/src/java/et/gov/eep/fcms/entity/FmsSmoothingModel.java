/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AB
 */
@Entity
//@Table(name = "fms_casher")
@XmlRootElement
public class FmsSmoothingModel implements Serializable {

    @Id
    private int id;
    private String weeks;
    private double usd_mt;
    private double facdtor_01;
    private double facdtor_02;
    private double facdtor_03;

    /**
     *
     */
    public FmsSmoothingModel() {
    }

    /**
     *
     * @return
     */
    public String getWeeks() {
        return weeks;
    }

    /**
     *
     * @param weeks
     */
    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    /**
     *
     * @return
     */
    public double getUsd_mt() {
        return usd_mt;
    }

    /**
     *
     * @param usd_mt
     */
    public void setUsd_mt(double usd_mt) {
        this.usd_mt = usd_mt;
    }

    /**
     *
     * @return
     */
    public double getFacdtor_01() {
        return facdtor_01;
    }

    /**
     *
     * @param facdtor_01
     */
    public void setFacdtor_01(double facdtor_01) {
        this.facdtor_01 = facdtor_01;
    }

    /**
     *
     * @return
     */
    public double getFacdtor_02() {
        return facdtor_02;
    }

    /**
     *
     * @param facdtor_02
     */
    public void setFacdtor_02(double facdtor_02) {
        this.facdtor_02 = facdtor_02;
    }

    /**
     *
     * @return
     */
    public double getFacdtor_03() {
        return facdtor_03;
    }

    /**
     *
     * @param facdtor_03
     */
    public void setFacdtor_03(double facdtor_03) {
        this.facdtor_03 = facdtor_03;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

}
