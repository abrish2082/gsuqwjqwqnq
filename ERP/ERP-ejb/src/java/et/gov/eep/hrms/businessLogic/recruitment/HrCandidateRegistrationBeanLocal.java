/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.recruitment;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.lookup.HrLuLanguages;
import et.gov.eep.hrms.entity.lookup.HrLuNationalities;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrCandidiates;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrCandidateRegistrationBeanLocal {

    public List<HrAdvertisements> batchCodes(String type);

    public List<HrAdvertisedJobs> advertizedJobs(int advertId);

    public HrAdvertisements findBatchCode(HrAdvertisements hrAdvertisements);

    public void save(HrCandidiates candidiates);

    public void edit(HrCandidiates candidiates);

    public HrLuNationalities findNationality(HrLuNationalities hrLuNationalities);

    public ArrayList<HrLuEducTypes> findEducationTypes();

    public ArrayList<HrLuEducLevels> findEducationLeves();

    public HrCandidiates getByCandidateId(HrCandidiates candidiates);

    public ArrayList<HrCandidiates> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String Col_Value);

    public HrCandidiates getByfirstName(HrCandidiates candidiates);

    public ArrayList<HrLuLanguages> findAllHrLuLanguages();

    public List<HrCandidiates> readCandidiates(int status1, int status2);

    public HrLuEducLevels findbyLuEduLevel(HrLuEducLevels hrLuEducLevels);

    public HrLuEducTypes findbyLuEduType(HrLuEducTypes hrLuEducTypes);

    public List<ColumnNameResolver> findColumns();



}
