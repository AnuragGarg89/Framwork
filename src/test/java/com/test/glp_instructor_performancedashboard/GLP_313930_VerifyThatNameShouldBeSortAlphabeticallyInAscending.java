/*
 * PEARSON PROPRIETARY AND CONFIDENTIAL INFORMATION SUBJECT TO NDA 
 * Copyright (c) 2017 Pearson Education, Inc.
 * All Rights Reserved. 
 * 
 * NOTICE: All information contained herein is, and remains the property of 
 * Pearson Education, Inc. The intellectual and technical concepts contained 
 * herein are proprietary to Pearson Education, Inc. and may be covered by U.S. 
 * and Foreign Patents, patent applications, and are protected by trade secret 
 * or copyright law. Dissemination of this information, reproduction of this  
 * material, and copying or distribution of this software is strictly forbidden   
 * unless prior written permission is obtained from Pearson Education, Inc.
 */
package com.test.glp_instructor_performancedashboard;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.groups.Groups;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPInstructor_CourseViewPage;
import com.glp.page.GLPInstructor_InstructorDashboardPage;

/**
 * @author mayank.mittal
 * @date Mar 12, 2018
 * @description :To verify that Name should be sort Alphabetically in Ascending
 *              order on the LastName
 * 
 */
public class GLP_313930_VerifyThatNameShouldBeSortAlphabeticallyInAscending
        extends BaseClass {
    public GLP_313930_VerifyThatNameShouldBeSortAlphabeticallyInAscending() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.INSTRUCTOR }, enabled = true,
            description = "To verify that Name should be sort Alphabetically in Ascending order on the LastName")
    public void
           verifyNameShouldBeSortedAlphabeticallyinMentionedOrderwrtLastName()
                   throws InterruptedException {
        startReport(getTestCaseId(),
                "To verify that Name should be sort Alphabetically in Ascending order on the LastName");

        try {
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(
                    configurationsXlsMap.get("INSTRUCTOR_USER_NAME"),
                    configurationsXlsMap.get("INSTRUCTOR_PASSWORD"));
            GLPInstructor_CourseViewPage objProductApplicationCourseViewPage = new GLPInstructor_CourseViewPage(
                    reportTestObj, APP_LOG);

            // Verify course tile on courseview page
            objProductApplicationCourseViewPage.verifyElementPresent(
                    "CourseTileInstructor",
                    "Courses associated with instruction displayed on Instructor homepage");

            GLPInstructor_InstructorDashboardPage objProductApplicationInstructorDashboardPage = new GLPInstructor_InstructorDashboardPage(
                    reportTestObj, APP_LOG);

            // Navigate to Performance dashboard
            objProductApplicationCourseViewPage
                    .navigateToPerformanceDashboard();

            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceStudentList",
                    "Verify Student List is clicked.");
            // check the default list sorting is in ascending order
            objProductApplicationInstructorDashboardPage.verifyUserNameOrder(
                    "InstructorDashBoardPerformanceFullName", "asc",
                    "Verify Student list is sorted in ascending order by name.");

            // Down arrow is clicked and list is converted in descending order.
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceNameAsc",
                    "Student list will be sorted in descending order by name.");
            objProductApplicationInstructorDashboardPage.verifyUserNameOrder(
                    "InstructorDashBoardPerformanceFullName", "desc",
                    "Verify Student list is sorted in ascending order by name.");

            // Up arrow is clicked and list is converted in ascending order.
            objProductApplicationInstructorDashboardPage.clickOnElement(
                    "InstructorDashBoardPerformanceNameDesc",
                    "Student list will be sorted in ascending order by name.");
            objProductApplicationInstructorDashboardPage.verifyUserNameOrder(
                    "InstructorDashBoardPerformanceFullName", "asc",
                    "Verify Student list is sorted in ascending order by name.");

        } finally {
            webDriver.quit();
            webDriver = null;
        }

    }
}
