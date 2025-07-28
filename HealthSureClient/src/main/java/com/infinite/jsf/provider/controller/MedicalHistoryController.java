package com.infinite.jsf.provider.controller;

import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.infinite.jsf.provider.dao.DoctorAvailabilityDao;
import com.infinite.jsf.provider.dao.MedicalHistoryDao;
import com.infinite.jsf.provider.model.MedicalProcedure;
import com.infinite.jsf.provider.model.PrescribedMedicines;
import com.infinite.jsf.provider.model.Prescription;
import com.infinite.jsf.provider.model.ProcedureDailyLog;
import com.infinite.jsf.provider.model.ProcedureTest;

public class MedicalHistoryController {

    private String doctorId;
    private String searchKey;
    private String searchType = "";
    private String nameSearchMode = "";
    private String procedureTypeSelected;

    //----------------Medical Procedure-------------------
    
    private List<MedicalProcedure> searchResults;
    private MedicalProcedure medicalProcedure;

    private MedicalHistoryDao medicalHistoryDao;

    private String sortColumn = "";
    private boolean sortAscending = true;
    
    private int currentPage = 1;
    private int pageSize = 5;

    //----------------Prescription-------------------
    
    private List<Prescription> allPrescriptions;
    private Prescription selectedPrescription;

    private int prescriptionCurrentPage = 1;
    private int prescriptionPageSize = 5;

    private String prescriptionSortColumn = "";
    private boolean prescriptionSortAscending = true;
    
    //----------------Prescribed Medicines-------------------

    private List<PrescribedMedicines> prescribedMedicines;
    private List<PrescribedMedicines> allMedicines = new ArrayList<>();

    private int medicineCurrentPage = 1;
    private int medicinePageSize = 5;

    private String medicineSortColumn = "";
    private boolean medicineSortAscending = true;
    
    //----------------Prescribed Tests-------------------
    
    private List<ProcedureTest> prescribedTests;
    
    private String testsSortColumn = "";
    private boolean testsSortAscending = true;

    private int testCurrentPage = 1;
    private int testPageSize = 5;
    
    //----------------Procedure Daily Log-------------------
    
    private List<ProcedureDailyLog> allLogs;
    
    private int logCurrentPage = 1;
    private int logPageSize = 5;

    private String logSortColumn = "";
    private boolean logSortAscending = true;
    
    

    public String searchProcedures() {
        FacesContext context = FacesContext.getCurrentInstance();

        // Validate Doctor ID
        if (doctorId == null || doctorId.trim().isEmpty()) {
            context.addMessage("historyForm:doctorId", 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Doctor ID is required.", null));
            return null;
        }
        
        doctorId = doctorId.trim().toUpperCase();

        if (!doctorId.matches("^DOC\\d{3}$")) {
            context.addMessage("historyForm:doctorId", 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Doctor ID must be in format DOC001.", null));
            return null;
        }

        if (!medicalHistoryDao.doctorExists(doctorId)) {
            context.addMessage("historyForm:doctorId", 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Doctor ID does not exist.", null));
            return null;
        }

        // Validate Procedure Type
        if (procedureTypeSelected == null || procedureTypeSelected.trim().isEmpty()) {
            context.addMessage("historyForm:procedureType", 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a procedure type.", null));
            return null;
        }

        procedureTypeSelected = procedureTypeSelected.trim();

        if (searchType == null || searchType.trim().isEmpty()) {
            // Basic doctor + type search
            searchResults = medicalHistoryDao.searchByDoctorIdWithDetails(doctorId, procedureTypeSelected);
            if (searchResults == null || searchResults.isEmpty()) {
                context.addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, 
                    "No procedures found for selected doctor and procedure type.", null));
                return null;
            }
        } else {
            // Filtered search
            if (searchKey == null || searchKey.trim().isEmpty()) {
                // Target the specific search key input based on search type
                String clientId = "historyForm:" + 
                    ("mobile".equals(searchType) ? "searchKeyMobile" : 
                     "hid".equals(searchType) ? "searchKeyHid" : "searchKeyName");
                context.addMessage(clientId, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Search value cannot be empty.", null));
                return null;
            }
            
            String trimmedKey = searchKey.trim();
            boolean isNameSearch = "name".equals(searchType);

            switch (searchType) {
                case "hid":
                    String normalizedHid = trimmedKey.toUpperCase();
                    if (!normalizedHid.matches("^HID\\d{3}$")) {
                        context.addMessage("historyForm:searchKeyHid", 
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Invalid HID format. Must be HID followed by 3 digits (e.g., HID001).", null));
                        return null;
                    }
                    searchResults = medicalHistoryDao.searchByHid(doctorId, normalizedHid, procedureTypeSelected);
                    break;

                case "name":
                    if (nameSearchMode == null || nameSearchMode.trim().isEmpty()) {
                        // EXACT MATCH SEARCH WHEN NO MODE SELECTED
                        searchResults = medicalHistoryDao.searchByNameExactMatch(doctorId, trimmedKey, procedureTypeSelected);
                        if (searchResults == null || searchResults.isEmpty()) {
                            context.addMessage("historyForm:searchKeyName", 
                                new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                "No exact match found for: " + trimmedKey, null));
                            return null;
                        }
                    } 
                    else if ("startsWith".equals(nameSearchMode)) {
                        searchResults = medicalHistoryDao.searchByNameStartsWith(doctorId, trimmedKey, procedureTypeSelected);
                    } 
                    else if ("contains".equals(nameSearchMode)) {
                        searchResults = medicalHistoryDao.searchByNameContains(doctorId, trimmedKey, procedureTypeSelected);
                    } 
                    else {
                        context.addMessage("historyForm:nameSearchMode", 
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid name search mode.", null));
                        return null;
                    }
                    break;

                case "mobile":
                    if (!trimmedKey.matches("^[6-9]\\d{9}$")) {
                        context.addMessage("historyForm:searchKeyMobile", 
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Invalid mobile number", null));
                        return null;
                    }
                    searchResults = medicalHistoryDao.searchByMobile(doctorId, trimmedKey, procedureTypeSelected);
                    break;

                default:
                    context.addMessage("historyForm:searchType", 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid search type.", null));
                    return null;
            }

            if (searchResults == null || searchResults.isEmpty()) {
                String message = isNameSearch 
                    ? "No matches found for the selected search criteria."
                    : "No procedures found for this search.";
                    
                context.addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, message, null));
                return null;
            }
        }

        currentPage = 1;
        return null;
    }
    public String confirmSearchType() {
        if (!"name".equals(searchType)) {
            nameSearchMode = "";
        }
        return null;
    }

    public String resetForm() {
        this.doctorId = null;
        this.searchKey = null;
        this.searchType = null;
        this.nameSearchMode = null;
        this.searchResults = null;
        this.sortColumn = null;
        this.sortAscending = true;
        this.currentPage = 0;
        this.allPrescriptions = null;
        this.medicalProcedure = null;
        this.procedureTypeSelected = null;
        return null;
    }

 // Replace the existing sortBy() method with these:
    public void sortByAsc(String column) {
        this.sortColumn = column;
        this.sortAscending = true;
        sortProcedures();
    }

    public void sortByDesc(String column) {
        this.sortColumn = column;
        this.sortAscending = false;
        sortProcedures();
    }

    private void sortProcedures() {
        if (searchResults != null) {
            Collections.sort(searchResults, new Comparator<MedicalProcedure>() {
                public int compare(MedicalProcedure p1, MedicalProcedure p2) {
                    int result = 0;
                    switch (sortColumn) {
                        case "procedureId":
                            result = safeCompare(p1.getProcedureId(), p2.getProcedureId());
                            break;
                        case "procedureDate":
                            result = safeCompare(p1.getProcedureDate(), p2.getProcedureDate());
                            break;
                        case "diagnosis":
                            result = safeCompare(p1.getDiagnosis(), p2.getDiagnosis());
                            break;
                        case "recommendations":
                            result = safeCompare(p1.getRecommendations(), p2.getRecommendations());
                            break;
                        case "recipientName":
                            String name1 = (p1.getRecipient() != null) ? 
                                (p1.getRecipient().getFirstName() + " " + p1.getRecipient().getLastName()) : "";
                            String name2 = (p2.getRecipient() != null) ? 
                                (p2.getRecipient().getFirstName() + " " + p2.getRecipient().getLastName()) : "";
                            result = name1.compareToIgnoreCase(name2);
                            break;
                        case "fromDate":
                            result = safeCompare(p1.getFromDate(), p2.getFromDate());
                            break;
                        case "toDate":
                            result = safeCompare(p1.getToDate(), p2.getToDate());
                            break;
                        default:
                            result = 0;
                    }
                    return sortAscending ? result : -result;
                }
            });
        }
        currentPage = 1;
        getPaginatedList();
    }

    private <T extends Comparable<T>> int safeCompare(T o1, T o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        return o1.compareTo(o2);
    }
    
    public boolean isAscDisabled(String column) {
        return column.equals(this.sortColumn) && this.sortAscending;
    }

    public boolean isDescDisabled(String column) {
        return column.equals(this.sortColumn) && !this.sortAscending;
    }
    
    public List<MedicalProcedure> getPaginatedList() {
        if (searchResults == null) return null;
        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, searchResults.size());
        return searchResults.subList(start, end);
    }

    public int getTotalProcedureRecords() {
        return (searchResults != null) ? searchResults.size() : 0;
    }
    
    public int getTotalPages() {
        return (searchResults == null || searchResults.isEmpty()) ? 1 :
                (int) Math.ceil((double) searchResults.size() / pageSize);
    }

    public void goToFirstProcedurePage() {
        currentPage = 1;
    }

    public void goToLastProcedurePage() {
        currentPage = getTotalPages();
    }
    public boolean isNextButtonDisabled() {
        return searchResults == null || ((currentPage + 1) * pageSize) >= searchResults.size();
    }

    public boolean isPreviousButtonDisabled() {
        return currentPage == 1;
    }

    public String nextPage() {
        if (currentPage < getTotalPages()) currentPage++;
        return null;
    }

    public String previousPage() {
        if (currentPage > 1) currentPage--;
        return null;
    }

    public String loadPrescriptions(MedicalProcedure procedure) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        // Initialize collections
        this.allPrescriptions = new ArrayList<>();
        this.prescribedMedicines = new ArrayList<>();
        this.prescribedTests = new ArrayList<>();
        
        // Check if procedure has any prescriptions
        if (procedure.getPrescriptions() == null || procedure.getPrescriptions().isEmpty()) {
            context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "No prescription found for procedure ID: " + procedure.getProcedureId(), 
                null));
            return null; // Stay on current page
        }
        
        // Load prescription details
        for (Prescription p : procedure.getPrescriptions()) {
            Prescription detailed = medicalHistoryDao.getPrescriptionWithDetails(p.getPrescriptionId());
            
            if (detailed != null) {
                this.allPrescriptions.add(detailed);
                
                if (detailed.getPrescribedMedicines() != null) {
                    this.prescribedMedicines.addAll(detailed.getPrescribedMedicines());
                }
                if (detailed.getTests() != null) {
                    this.prescribedTests.addAll(detailed.getTests());
                }
            }
        }
        
        resetPrescriptionPagination();
        return "Prescription.jsf?faces-redirect=true";
    }
    
 // Replace sortMedicinesBy() with:
    public void sortMedicinesByAsc(String column) {
        this.medicineSortColumn = column;
        this.medicineSortAscending = true;
        sortMedicines();
    }

    public void sortMedicinesByDesc(String column) {
        this.medicineSortColumn = column;
        this.medicineSortAscending = false;
        sortMedicines();
    }

    private void sortMedicines() {
        if (allMedicines != null) {
            Collections.sort(allMedicines, new Comparator<PrescribedMedicines>() {
                public int compare(PrescribedMedicines m1, PrescribedMedicines m2) {
                    int result = 0;
                    switch (medicineSortColumn) {
                        case "prescribedId":
                            result = safeCompare(m1.getPrescribedId(), m2.getPrescribedId());
                            break;
                        case "medicineName":
                            result = safeCompare(m1.getMedicineName(), m2.getMedicineName());
                            break;
                        case "dosage":
                            result = safeCompare(m1.getDosage(), m2.getDosage());
                            break;
                        case "type":
                            result = safeCompare(m1.getType(), m2.getType());
                            break;
                        case "duration":
                            result = safeCompare(m1.getDuration(), m2.getDuration());
                            break;
                        case "startDate":
                            result = safeCompare(m1.getStartDate(), m2.getStartDate());
                            break;
                        case "endDate":
                            result = safeCompare(m1.getEndDate(), m2.getEndDate());
                            break;
                        default:
                            result = 0;
                    }
                    return medicineSortAscending ? result : -result;
                }
            });
        }
        medicineCurrentPage = 1;
        getPaginatedMedicines();
    }

    public boolean isMedicineAscDisabled(String column) {
        return column.equals(this.medicineSortColumn) && this.medicineSortAscending;
    }

    public boolean isMedicineDescDisabled(String column) {
        return column.equals(this.medicineSortColumn) && !this.medicineSortAscending;
    }

    public String viewMedicinesForSelectedPrescription(Prescription prescription) {
        FacesContext context = FacesContext.getCurrentInstance();
        
        this.selectedPrescription = prescription;
        
        // Check if prescription has medicines
        if (prescription.getPrescribedMedicines() == null || prescription.getPrescribedMedicines().isEmpty()) {
            context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "No prescribed medicines found for prescription ID: " + prescription.getPrescriptionId(), 
                null));
            
            // Clear existing medicines list
            this.allMedicines = new ArrayList<>();
            this.medicineCurrentPage = 1;
            
            // Stay on current page but show message
            return null;
        }
        
        // If medicines exist, proceed as before
        this.allMedicines = new ArrayList<>(selectedPrescription.getPrescribedMedicines());
        this.medicineCurrentPage = 1;
        this.medicineSortColumn = "";
        this.medicineSortAscending = true;
        
        return "PrescribedMedicines?faces-redirect=true";
    }

    public int getTotalMedicineRecords() {
        return (allMedicines != null) ? allMedicines.size() : 0;
    }
    
    public int getMedicineTotalPages() {
        if (allMedicines == null || allMedicines.isEmpty()) {
            return 1;
        }
        return (int) Math.ceil((double) allMedicines.size() / medicinePageSize);
    }

    public List<PrescribedMedicines> getPaginatedMedicines() {
        int startIndex = (medicineCurrentPage - 1) * medicinePageSize;
        int endIndex = Math.min(startIndex + medicinePageSize, allMedicines.size());
        return allMedicines.subList(startIndex, endIndex);
    }

    public String nextMedicinePage() {
        if (medicineCurrentPage < getMedicineTotalPages()) {
            medicineCurrentPage++;
        }
        return null;
    }

    public boolean isMedicineNextDisabled() {
        return medicineCurrentPage >= getMedicineTotalPages();
    }

    public boolean isMedicinePreviousDisabled() {
        return medicineCurrentPage <= 1;
    }

    public int getMedicineCurrentPage() {
        return medicineCurrentPage;
    }

    public String previousMedicinePage() {
        if (medicineCurrentPage > 1) {
            medicineCurrentPage--;
        }
        return null;
    }

    public void goToFirstMedicinePage() {
        medicineCurrentPage = 1;
    }

    public void goToLastMedicinePage() {
        medicineCurrentPage = getMedicineTotalPages();
    }

    
 // Replace sortTestsBy() with:
    public void sortTestsByAsc(String column) {
        this.testsSortColumn = column;
        this.testsSortAscending = true;
        sortTests();
    }

    public void sortTestsByDesc(String column) {
        this.testsSortColumn = column;
        this.testsSortAscending = false;
        sortTests();
    }

    private void sortTests() {
        if (prescribedTests != null) {
            Collections.sort(prescribedTests, new Comparator<ProcedureTest>() {
                public int compare(ProcedureTest t1, ProcedureTest t2) {
                    int result = 0;
                    switch (testsSortColumn) {
                        case "testId":
                            result = safeCompare(t1.getTestId(), t2.getTestId());
                            break;
                        case "testName":
                            result = safeCompare(t1.getTestName(), t2.getTestName());
                            break;
                        case "testDate":
                            result = safeCompare(t1.getTestDate(), t2.getTestDate());
                            break;
                        case "resultSummary":
                            result = safeCompare(t1.getResultSummary(), t2.getResultSummary());
                            break;
                        default:
                            result = 0;
                    }
                    return testsSortAscending ? result : -result;
                }
            });
        }
        testCurrentPage = 1;
        getPaginatedTests();
    }

    public boolean isTestAscDisabled(String column) {
        return column.equals(this.testsSortColumn) && this.testsSortAscending;
    }

    public boolean isTestDescDisabled(String column) {
        return column.equals(this.testsSortColumn) && !this.testsSortAscending;
    }
    
    public String viewTestsForSelectedPrescription(Prescription prescription) {
        FacesContext context = FacesContext.getCurrentInstance();

        this.selectedPrescription = prescription;
        
        if (prescription.getTests() == null || prescription.getTests().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
            		"No tests found for this prescription ID: " + prescription.getPrescriptionId(),
            		null));
            this.prescribedTests = new ArrayList<>();
            this.testCurrentPage = 1;
            
            return null;
        }
        
        this.prescribedTests = new ArrayList<>(selectedPrescription.getTests());
        this.testCurrentPage = 1;
        this.testsSortColumn = "";
        this.testsSortAscending = true;
        
        return "ProcedureTest?faces-redirect=true";
    }

    
    
    public List<ProcedureTest> getPaginatedTests() {
        int start = (testCurrentPage - 1) * testPageSize;
        int end = Math.min(start + testPageSize, prescribedTests.size());
        return prescribedTests.subList(start, end);
    }

    public int getTotalTestRecords() {
        return (prescribedTests != null) ? prescribedTests.size() : 0;
    }
    
    public int getTestTotalPages() {
        if (prescribedTests == null || prescribedTests.isEmpty()) {
            return 1;
        }
        return (int) Math.ceil((double) prescribedTests.size() / testPageSize);
    }

    public void goToFirstTestPage() {
        testCurrentPage = 1;
        getPaginatedTests();
    }

    public void goToLastTestPage() {
        testCurrentPage = getTestTotalPages();
        getPaginatedTests();
    }
    public String nextTestPage() {
        if (testCurrentPage < getTestTotalPages()) testCurrentPage++;
        return null;
    }

    public String previousTestPage() {
        if (testCurrentPage > 1) testCurrentPage--;
        return null;
    }

    public boolean isTestNextDisabled() {
        return testCurrentPage >= getTestTotalPages();
    }

    public boolean isTestPreviousDisabled() {
        return testCurrentPage <= 1;
    }

 // Replace sortLogsBy() with:
    public void sortLogsByAsc(String column) {
        this.logSortColumn = column;
        this.logSortAscending = true;
        sortLogs();
    }

    public void sortLogsByDesc(String column) {
        this.logSortColumn = column;
        this.logSortAscending = false;
        sortLogs();
    }

    private void sortLogs() {
        if (allLogs != null) {
            Collections.sort(allLogs, new Comparator<ProcedureDailyLog>() {
                public int compare(ProcedureDailyLog l1, ProcedureDailyLog l2) {
                    int result = 0;
                    switch (logSortColumn) {
                        case "logDate":
                            result = safeCompare(l1.getLogDate(), l2.getLogDate());
                            break;
                        case "vitals":
                            result = safeCompare(l1.getVitals(), l2.getVitals());
                            break;
                        case "notes":
                            result = safeCompare(l1.getNotes(), l2.getNotes());
                            break;
                        default:
                            result = 0;
                    }
                    return logSortAscending ? result : -result;
                }
            });
        }
        logCurrentPage = 1;
        getPaginatedLogs();
    }
    
    public boolean isLogAscDisabled(String column) {
        return column.equals(this.logSortColumn) && this.logSortAscending;
    }

    public boolean isLogDescDisabled(String column) {
        return column.equals(this.logSortColumn) && !this.logSortAscending;
    }

    public String loadProcedureLogs(MedicalProcedure procedure) {
        FacesContext context = FacesContext.getCurrentInstance();

        this.medicalProcedure = medicalHistoryDao.getProcedureWithLogs(procedure.getProcedureId());
        
        if (this.medicalProcedure != null && this.medicalProcedure.getLogs() != null) {
            this.allLogs = new ArrayList<>(this.medicalProcedure.getLogs());
        } else {
            this.allLogs = new ArrayList<>();
        }

        if (procedure.getLogs() == null || procedure.getLogs().isEmpty()) {
            context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "No logs found for procedure ID: " + procedure.getProcedureId(), 
                null));
            return null; // Stay on current page
        }
        
        this.logCurrentPage = 1;
        this.logSortColumn = "";
        this.logSortAscending = true;

        getPaginatedLogs();
        System.out.println(getPaginatedLogs());

        
        return "ProcedureDailyLog.jsf?faces-redirect=true";
    }

    public int getTotalLogRecords() {
        return (allLogs != null) ? allLogs.size() : 0;
    }
    
    public int getTotalLogPages() {
        return (int) Math.ceil((double) allLogs.size() / logPageSize);
    }

    public void goToFirstLogPage() {
        logCurrentPage = 1;
        getPaginatedLogs();
    }

    public void goToLastLogPage() {
        logCurrentPage = getTotalLogPages();
        getPaginatedLogs();
    }

    public void goToNextLogPage() {
        if (logCurrentPage < getTotalLogPages()) {
            logCurrentPage++;
            getPaginatedLogs();
        }
    }

    public void goToPreviousLogPage() {
        if (logCurrentPage > 1) {
            logCurrentPage--;
            getPaginatedLogs();
        }
    }

    public boolean isLogsNextDisabled() {
        return logCurrentPage >= getTotalLogPages();
    }

    public boolean isLogsPreviousDisabled() {
        return logCurrentPage <= 1;
    }
    
    public List<ProcedureDailyLog> getPaginatedLogs() {
        int start = (logCurrentPage - 1) * logPageSize;
        int end = Math.min(start + logPageSize, allLogs.size());
        return allLogs.subList(start, end);
    }

 // Replace sortPrescriptionsBy() with:
    public void sortPrescriptionsByAsc(String column) {
        this.prescriptionSortColumn = column;
        this.prescriptionSortAscending = true;
        sortPrescriptions();
    }

    public void sortPrescriptionsByDesc(String column) {
        this.prescriptionSortColumn = column;
        this.prescriptionSortAscending = false;
        sortPrescriptions();
    }

    private void sortPrescriptions() {
        if (allPrescriptions != null) {
            Collections.sort(allPrescriptions, new Comparator<Prescription>() {
                public int compare(Prescription p1, Prescription p2) {
                    int result = 0;
                    switch (prescriptionSortColumn) {
                        case "prescriptionId":
                            result = safeCompare(p1.getPrescriptionId(), p2.getPrescriptionId());
                            break;
                        case "writtenOn":
                            result = safeCompare(p1.getWrittenOn(), p2.getWrittenOn());
                            break;
                        case "startDate":
                            result = safeCompare(p1.getStartDate(), p2.getStartDate());
                            break;
                        case "endDate":
                            result = safeCompare(p1.getEndDate(), p2.getEndDate());
                            break;
                        case "doctorName":
                            String doc1 = (p1.getDoctor() != null) ? p1.getDoctor().getDoctorName() : "";
                            String doc2 = (p2.getDoctor() != null) ? p2.getDoctor().getDoctorName() : "";
                            result = doc1.compareToIgnoreCase(doc2);
                            break;
                        default:
                            result = 0;
                    }
                    return prescriptionSortAscending ? result : -result;
                }
            });
        }
        prescriptionCurrentPage = 1;
    }

    public boolean isPrescriptionAscDisabled(String column) {
        return column.equals(this.prescriptionSortColumn) && this.prescriptionSortAscending;
    }

    public boolean isPrescriptionDescDisabled(String column) {
        return column.equals(this.prescriptionSortColumn) && !this.prescriptionSortAscending;
    }

    public List<Prescription> getPaginatedPrescriptions() {
        if (allPrescriptions == null || allPrescriptions.isEmpty()) {
            return Collections.emptyList();
        }

        int totalSize = allPrescriptions.size();
        int start = (prescriptionCurrentPage - 1) * prescriptionPageSize;
        int end = Math.min(start + prescriptionPageSize, totalSize);

        if (start >= totalSize || start < 0 || end < 0 || start > end) {
            return Collections.emptyList();
        }

        return allPrescriptions.subList(start, end);
    }

    public int getTotalPrescriptionRecords() {
        return (allPrescriptions != null) ? allPrescriptions.size() : 0;
    }
    
    public int getTotalPrescriptionPages() {
        return (allPrescriptions == null || allPrescriptions.isEmpty()) ? 1 :
                (int) Math.ceil((double) allPrescriptions.size() / prescriptionPageSize);
    }

    public void goToFirstPrescriptionPage() {
        prescriptionCurrentPage = 1;
        getPaginatedPrescriptions();
    }

    public void goToLastPrescriptionPage() {
        prescriptionCurrentPage = getTotalPrescriptionPages();
        getPaginatedPrescriptions();
    }

    public void goToNextPrescriptionPage() {
        if (prescriptionCurrentPage < getTotalPrescriptionPages()) {
            prescriptionCurrentPage++;
            getPaginatedPrescriptions();
        }
    }

    public void goToPreviousPrescriptionPage() {
        if (prescriptionCurrentPage > 1) {
            prescriptionCurrentPage--;
            getPaginatedPrescriptions();
        }
    }

    public boolean isPrescriptionNextDisabled() {
        return prescriptionCurrentPage >= getTotalPrescriptionPages();
    }

    public boolean isPrescriptionPreviousDisabled() {
        return prescriptionCurrentPage <= 1;
    }

    public void resetPrescriptionPagination() {
        prescriptionCurrentPage = 1;
    }
    
    public String backToPrescription() {
    	return "Prescription.jsp?faces-redirect=true";
    }
    
    public String backToMedicalProcedure( ) {
    	return "MedicalProcedureSearch.jsf?faces-redirect=true";
    }
    // === Getters and Setters ===

    
	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getNameSearchMode() {
		return nameSearchMode;
	}

	public void setNameSearchMode(String nameSearchMode) {
		this.nameSearchMode = nameSearchMode;
	}

	public List<MedicalProcedure> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<MedicalProcedure> searchResults) {
		this.searchResults = searchResults;
	}

	public MedicalProcedure getMedicalProcedure() {
		return medicalProcedure;
	}

	public void setMedicalProcedure(MedicalProcedure medicalProcedure) {
		this.medicalProcedure = medicalProcedure;
	}

	public MedicalHistoryDao getMedicalHistoryDao() {
		return medicalHistoryDao;
	}

	public void setMedicalHistoryDao(MedicalHistoryDao medicalHistoryDao) {
		this.medicalHistoryDao = medicalHistoryDao;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public boolean isSortAscending() {
		return sortAscending;
	}

	public void setSortAscending(boolean sortAscending) {
		this.sortAscending = sortAscending;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<Prescription> getallPrescriptions() {
		return allPrescriptions;
	}

	public void setallPrescriptions(List<Prescription> allPrescriptions) {
		this.allPrescriptions = allPrescriptions;
	}

	public String getPrescriptionSortColumn() {
		return prescriptionSortColumn;
	}

	public void setPrescriptionSortColumn(String prescriptionSortColumn) {
		this.prescriptionSortColumn = prescriptionSortColumn;
	}

	public boolean isPrescriptionSortAscending() {
		return prescriptionSortAscending;
	}

	public void setPrescriptionSortAscending(boolean prescriptionSortAscending) {
		this.prescriptionSortAscending = prescriptionSortAscending;
	}

	public int getPrescriptionCurrentPage() {
		return prescriptionCurrentPage;
	}

	public void setPrescriptionCurrentPage(int prescriptionCurrentPage) {
		this.prescriptionCurrentPage = prescriptionCurrentPage;
	}

	public int getPrescriptionPageSize() {
		return prescriptionPageSize;
	}

	public void setPrescriptionPageSize(int prescriptionPageSize) {
		this.prescriptionPageSize = prescriptionPageSize;
	}

	public String getProcedureTypeSelected() {
		return procedureTypeSelected;
	}

	public void setProcedureTypeSelected(String procedureTypeSelected) {
		this.procedureTypeSelected = procedureTypeSelected;
	}

	public List<PrescribedMedicines> getPrescribedMedicines() {
		return prescribedMedicines;
	}

	public void setPrescribedMedicines(List<PrescribedMedicines> prescribedMedicines) {
		this.prescribedMedicines = prescribedMedicines;
	}

	public List<ProcedureTest> getPrescribedTests() {
		return prescribedTests;
	}

	public void setPrescribedTests(List<ProcedureTest> prescribedTests) {
		this.prescribedTests = prescribedTests;
	}

	
	public Prescription getSelectedPrescription() {
		return selectedPrescription;
	}

	public void setSelectedPrescription(Prescription selectedPrescription) {
	    this.selectedPrescription = selectedPrescription;
	    if (selectedPrescription != null && selectedPrescription.getPrescribedMedicines() != null) {
	        this.allMedicines = new ArrayList<>(selectedPrescription.getPrescribedMedicines());
	        this.medicineCurrentPage = 1; // Reset to first page
	    } else {
	        this.allMedicines = new ArrayList<>();
	    }
	}

	public List<PrescribedMedicines> getMedicinesForSelectedPrescription() {
	    return getPaginatedMedicines();
	}

	public String getMedicineSortColumn() {
		return medicineSortColumn;
	}

	public void setMedicineSortColumn(String medicineSortColumn) {
		this.medicineSortColumn = medicineSortColumn;
	}

	public boolean isMedicineSortAscending() {
		return medicineSortAscending;
	}

	public void setMedicineSortAscending(boolean medicineSortAscending) {
		this.medicineSortAscending = medicineSortAscending;
	}

	public String getTestsSortColumn() {
		return testsSortColumn;
	}

	public void setTestsSortColumn(String testsSortColumn) {
		this.testsSortColumn = testsSortColumn;
	}

	public boolean isTestsSortAscending() {
		return testsSortAscending;
	}

	public void setTestsSortAscending(boolean testsSortAscending) {
		this.testsSortAscending = testsSortAscending;
	}

	public List<ProcedureDailyLog> getAllLogs() {
	    return allLogs;
	}

	public void setAllLogs(List<ProcedureDailyLog> allLogs) {
	    this.allLogs = allLogs;
	}


	public int getLogCurrentPage() {
	    return logCurrentPage;
	}

	public String getLogSortColumn() {
	    return logSortColumn;
	}

	public boolean isLogSortAscending() {
	    return logSortAscending;
	}

	public void setLogSortColumn(String logSortColumn) {
	    this.logSortColumn = logSortColumn;
	}

	public void setLogSortAscending(boolean logSortAscending) {
	    this.logSortAscending = logSortAscending;
	}

	public List<Prescription> getAllPrescriptions() {
		return allPrescriptions;
	}

	public void setAllPrescriptions(List<Prescription> allPrescriptions) {
		this.allPrescriptions = allPrescriptions;
	}

	public List<PrescribedMedicines> getAllMedicines() {
		return allMedicines;
	}

	public void setAllMedicines(List<PrescribedMedicines> allMedicines) {
		this.allMedicines = allMedicines;
	}

	public int getMedicinePageSize() {
		return medicinePageSize;
	}

	public void setMedicinePageSize(int medicinePageSize) {
		this.medicinePageSize = medicinePageSize;
	}

	public int getTestCurrentPage() {
		return testCurrentPage;
	}

	public void setTestCurrentPage(int testCurrentPage) {
		this.testCurrentPage = testCurrentPage;
	}

	public int getTestPageSize() {
		return testPageSize;
	}

	public void setTestPageSize(int testPageSize) {
		this.testPageSize = testPageSize;
	}

	public int getLogPageSize() {
		return logPageSize;
	}

	public void setLogPageSize(int logPageSize) {
		this.logPageSize = logPageSize;
	}

	public void setMedicineCurrentPage(int medicineCurrentPage) {
		this.medicineCurrentPage = medicineCurrentPage;
	}

	public void setLogCurrentPage(int logCurrentPage) {
		this.logCurrentPage = logCurrentPage;
	}
}
