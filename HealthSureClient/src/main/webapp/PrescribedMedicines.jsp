<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<html>
<head>
    <title>Prescribed Medicines</title>
    <style>
        body {
            background-color: #f3f4f6;
            font-family: Arial, sans-serif;
        }

        .container {
            max-width: 900px;
            margin: 40px auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }

        .heading {
            text-align: center;
            font-size: 22px;
            font-weight: bold;
            color: #2563eb;
            margin-bottom: 25px;
        }

        .btn {
            background-color: #4b5563;
            color: white;
            padding: 8px 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn:hover {
            background-color: #1f2937;
        }

        .table-container {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 10px;
            text-align: center;
            border: 1px solid #ccc;
        }

        th {
            background-color: #f1f5f9;
            font-weight: bold;
        }

        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 20px;
            margin-top: 25px;
        }

        .pagination a {
            color: #2563eb;
            text-decoration: none;
            font-weight: bold;
        }

        .pagination a:hover {
            text-decoration: underline;
        }

        .sort-header {
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .sort-icons {
            display: inline-flex;
            flex-direction: column;
            margin-left: 5px;
        }

        .sort-icon {
            font-size: 0.7em;
            line-height: 1;
            cursor: pointer;
        }

        .sort-icon:hover {
            color: #007bff;
        }
        
        .info-text {
            margin-bottom: 15px;
            font-size: 14px;
            color: #4b5563;
        }
    </style>
</head>
<body>
<f:view>
    <h:form id="medicineForm" styleClass="container">

        <!-- Title -->
        <div class="heading">
            Prescribed Medicines for Prescription ID
            <h:outputText value="#{medicalHistoryController.selectedPrescription.prescriptionId}" />
        </div>

        <!-- Back Button -->
        <div style="margin-bottom: 20px;">
            <h:commandButton value="Back to Prescriptions"
                             action="#{medicalHistoryController.backToPrescription}"
                             styleClass="btn"
                             immediate="true" />
        </div>

        <!-- Total Records -->
        <div class="info-text">
            <h:outputText value="Total Records: #{medicalHistoryController.totalMedicineRecords}"/>
        </div>

        <!-- Medicines Table -->
        <div class="table-container">
            <h:dataTable value="#{medicalHistoryController.paginatedMedicines}" var="med"
                         styleClass="medicine-table"
                         rendered="#{not empty medicalHistoryController.allMedicines}">

                <!-- Prescribed ID -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Prescribed ID" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByAsc('prescribedId')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'prescribedId' and medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByDesc('prescribedId')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'prescribedId' and not medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{med.prescribedId}" />
                </h:column>

                <!-- Medicine Name -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Medicine Name" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByAsc('medicineName')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'medicineName' and medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByDesc('medicineName')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'medicineName' and not medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{med.medicineName}" />
                </h:column>

                <!-- Dosage -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Dosage" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByAsc('dosage')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'dosage' and medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByDesc('dosage')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'dosage' and not medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{med.dosage}" />
                </h:column>

                <!-- Type -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Type" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByAsc('type')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'type' and medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByDesc('type')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'type' and not medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{med.type}" />
                </h:column>

                <!-- Duration -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Duration" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByAsc('duration')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'duration' and medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByDesc('duration')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'duration' and not medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{med.duration}" />
                </h:column>

                <!-- Start Date -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Start Date" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByAsc('startDate')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'startDate' and medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByDesc('startDate')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'startDate' and not medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{med.startDate}">
                        <f:convertDateTime pattern="dd-MM-yyyy" />
                    </h:outputText>
                </h:column>

                <!-- End Date -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="End Date" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByAsc('endDate')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'endDate' and medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortMedicinesByDesc('endDate')}" 
                                               rendered="#{!(medicalHistoryController.medicineSortColumn eq 'endDate' and not medicalHistoryController.medicineSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{med.endDate}">
                        <f:convertDateTime pattern="dd-MM-yyyy" />
                    </h:outputText>
                </h:column>

            </h:dataTable>
        </div>

        <!-- Pagination Controls -->
        <div class="pagination">
            <h:panelGroup layout="block" styleClass="pagination-panel">
                <h:commandButton value="First" 
                                 action="#{medicalHistoryController.goToFirstMedicinePage}" 
                                 styleClass="btn"
                                 disabled="#{medicalHistoryController.medicineCurrentPage == 1}" />
                <h:commandButton value="Previous" 
                                 action="#{medicalHistoryController.previousMedicinePage}" 
                                 styleClass="btn"
                                 disabled="#{medicalHistoryController.medicineCurrentPage == 1}" />
                <h:outputText value="Page #{medicalHistoryController.medicineCurrentPage} of #{medicalHistoryController.medicineTotalPages}" />
                <h:commandButton value="Next" 
                                 action="#{medicalHistoryController.nextMedicinePage}" 
                                 styleClass="btn"
                                 disabled="#{medicalHistoryController.medicineCurrentPage == medicalHistoryController.medicineTotalPages}" />
                <h:commandButton value="Last" 
                                 action="#{medicalHistoryController.goToLastMedicinePage}" 
                                 styleClass="btn"
                                 disabled="#{medicalHistoryController.medicineCurrentPage == medicalHistoryController.medicineTotalPages}" />
            </h:panelGroup>
        </div>

    </h:form>
</f:view>
</body>
</html>