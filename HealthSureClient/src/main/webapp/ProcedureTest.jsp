<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<html>
<head>
    <title>Prescribed Tests</title>
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
    <h:form id="testForm" styleClass="container">
    
    <h:messages globalOnly="true" style="color:red; margin:10px 0;" layout="list" />
    

        <!-- Title -->
        <div class="heading">
            Prescribed Tests for Prescription ID
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
            <h:outputText value="Total Records: #{medicalHistoryController.totalTestRecords}"/>
        </div>

        <!-- Tests Table -->
        <div class="table-container">
            <h:dataTable value="#{medicalHistoryController.paginatedTests}" var="test"
                         styleClass="test-table"
                         rendered="#{not empty medicalHistoryController.prescribedTests}">

                <!-- Test ID -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Test ID" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortTestsByAsc('testId')}" 
                                               rendered="#{!(medicalHistoryController.testsSortColumn eq 'testId' and medicalHistoryController.testsSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortTestsByDesc('testId')}" 
                                               rendered="#{!(medicalHistoryController.testsSortColumn eq 'testId' and not medicalHistoryController.testsSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{test.testId}" />
                </h:column>

                <!-- Test Name -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Test Name" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortTestsByAsc('testName')}" 
                                               rendered="#{!(medicalHistoryController.testsSortColumn eq 'testName' and medicalHistoryController.testsSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortTestsByDesc('testName')}" 
                                               rendered="#{!(medicalHistoryController.testsSortColumn eq 'testName' and not medicalHistoryController.testsSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{test.testName}" />
                </h:column>

                <!-- Test Date -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Test Date" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortTestsByAsc('testDate')}" 
                                               rendered="#{!(medicalHistoryController.testsSortColumn eq 'testDate' and medicalHistoryController.testsSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortTestsByDesc('testDate')}" 
                                               rendered="#{!(medicalHistoryController.testsSortColumn eq 'testDate' and not medicalHistoryController.testsSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{test.testDate}">
                        <f:convertDateTime pattern="dd-MM-yyyy" />
                    </h:outputText>
                </h:column>

                <!-- Result Summary -->
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Result Summary" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{medicalHistoryController.sortTestsByAsc('resultSummary')}" 
                                               rendered="#{!(medicalHistoryController.testsSortColumn eq 'resultSummary' and medicalHistoryController.testsSortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{medicalHistoryController.sortTestsByDesc('resultSummary')}" 
                                               rendered="#{!(medicalHistoryController.testsSortColumn eq 'resultSummary' and not medicalHistoryController.testsSortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{test.resultSummary}" />
                </h:column>

            </h:dataTable>
        </div>

        <!-- Pagination Controls -->
        <div class="pagination">
            <h:panelGroup layout="block" styleClass="pagination-panel">
                <h:commandButton value="First" 
                                 action="#{medicalHistoryController.goToFirstTestPage}" 
                                 styleClass="btn"
                                 disabled="#{medicalHistoryController.testCurrentPage == 1}" />
                <h:commandButton value="Previous" 
                                 action="#{medicalHistoryController.previousTestPage}" 
                                 styleClass="btn"
                                 disabled="#{medicalHistoryController.testCurrentPage == 1}" />
                <h:outputText value="Page #{medicalHistoryController.testCurrentPage} of #{medicalHistoryController.testTotalPages}" />
                <h:commandButton value="Next" 
                                 action="#{medicalHistoryController.nextTestPage}" 
                                 styleClass="btn"
                                 disabled="#{medicalHistoryController.testCurrentPage == medicalHistoryController.testTotalPages}" />
                <h:commandButton value="Last" 
                                 action="#{medicalHistoryController.goToLastTestPage}" 
                                 styleClass="btn"
                                 disabled="#{medicalHistoryController.testCurrentPage == medicalHistoryController.testTotalPages}" />
            </h:panelGroup>
        </div>

    </h:form>
</f:view>
</body>
</html>