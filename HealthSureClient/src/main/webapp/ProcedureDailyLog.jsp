<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<html>
<head>
    <title>Procedure Daily Logs</title>
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

        .procedure-info {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<f:view>
    <h:form id="logForm" styleClass="container">
        <h2 class="heading">Procedure Daily Log</h2>
        
        <!-- Back Button -->
        <div style="margin-bottom: 20px;">
            <h:commandButton value="Back to Prescriptions"
                             action="#{medicalHistoryController.backToMedicalProcedure}"
                             styleClass="btn"
                             immediate="true" />
        </div>
        
        <div class="procedure-info">
            <h:panelGrid columns="2" cellpadding="5">
                <h:outputText value="Procedure ID:" style="font-weight:bold" />
                <h:outputText value="#{medicalHistoryController.medicalProcedure.procedureId}" />

                <h:outputText value="Diagnosis:" style="font-weight:bold" />
                <h:outputText value="#{medicalHistoryController.medicalProcedure.diagnosis}" />

                <h:outputText value="Recipient:" style="font-weight:bold" />
                <h:outputText value="#{medicalHistoryController.medicalProcedure.recipient.firstName} #{medicalHistoryController.medicalProcedure.recipient.lastName}" />
            </h:panelGrid>
        </div>

        <hr/>

        <h3>Daily Logs</h3>
        <div class="info-text">
            <h:outputText value="Total Records: #{medicalHistoryController.totalLogRecords}"/>
        </div>

        <div class="table-container">
            <h:dataTable value="#{medicalHistoryController.paginatedLogs}" var="log" 
                         styleClass="log-table" border="1" cellpadding="5">
            
                <!-- Date Column -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Date" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortLogsByAsc('logDate')}" 
                                               rendered="#{!(medicalHistoryController.logSortColumn eq 'logDate' and medicalHistoryController.logSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortLogsByDesc('logDate')}" 
                                               rendered="#{!(medicalHistoryController.logSortColumn eq 'logDate' and not medicalHistoryController.logSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{log.logDate}">
                        <f:convertDateTime pattern="yyyy-MM-dd" />
                    </h:outputText>
                </h:column>

                <!-- Notes Column -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Notes" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortLogsByAsc('notes')}" 
                                               rendered="#{!(medicalHistoryController.logSortColumn eq 'notes' and medicalHistoryController.logSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortLogsByDesc('notes')}" 
                                               rendered="#{!(medicalHistoryController.logSortColumn eq 'notes' and not medicalHistoryController.logSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{log.notes}" />
                </h:column>

                <!-- Vital Signs Column -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Vital Signs" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortLogsByAsc('vitals')}" 
                                               rendered="#{!(medicalHistoryController.logSortColumn eq 'vitals' and medicalHistoryController.logSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortLogsByDesc('vitals')}" 
                                               rendered="#{!(medicalHistoryController.logSortColumn eq 'vitals' and not medicalHistoryController.logSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{log.vitals}" />
                </h:column>
            </h:dataTable>
        </div>

        <div class="pagination">
            <h:panelGroup layout="block" styleClass="pagination-panel">
                <h:commandButton value="First" 
                                action="#{medicalHistoryController.goToFirstLogPage}" 
                                styleClass="btn"
                                disabled="#{medicalHistoryController.logCurrentPage == 1}" />
                <h:commandButton value="Previous" 
                                action="#{medicalHistoryController.goToPreviousLogPage}" 
                                styleClass="btn"
                                disabled="#{medicalHistoryController.logCurrentPage == 1}" />
                <h:outputText value="Page #{medicalHistoryController.logCurrentPage} of #{medicalHistoryController.totalLogPages}" />
                <h:commandButton value="Next" 
                                action="#{medicalHistoryController.goToNextLogPage}" 
                                styleClass="btn"
                                disabled="#{medicalHistoryController.logCurrentPage == medicalHistoryController.totalLogPages}" />
                <h:commandButton value="Last" 
                                action="#{medicalHistoryController.goToLastLogPage}" 
                                styleClass="btn"
                                disabled="#{medicalHistoryController.logCurrentPage == medicalHistoryController.totalLogPages}" />
            </h:panelGroup>
        </div>

        <br/>
        <h:commandButton value="Back to Procedures" 
                        action="MedicalProcedureSearch?faces-redirect=true" 
                        styleClass="btn" />
    </h:form>
</f:view>
</body>
</html>