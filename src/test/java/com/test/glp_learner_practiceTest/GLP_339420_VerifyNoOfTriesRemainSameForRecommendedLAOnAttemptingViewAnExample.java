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
package com.test.glp_learner_practiceTest;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.groups.Groups;
import com.autofusion.util.CommonUtil;
import com.glp.page.GLPConsole_LoginPage;
import com.glp.page.GLPLearner_CourseHomePage;
import com.glp.page.GLPLearner_CourseViewPage;
import com.glp.page.GLPLearner_DiagnosticTestPage;
import com.glp.page.GLPLearner_PracticeTestPage;
import com.glp.util.GLP_Utilities;

/**
 * @author Saurabh.Sharma
 * @date May 28, 2018
 * @description: Verify number of tries on recommended learning aids remain same
 *               when VAE is closed using Close button
 */

public class GLP_339420_VerifyNoOfTriesRemainSameForRecommendedLAOnAttemptingViewAnExample
        extends BaseClass {
    public GLP_339420_VerifyNoOfTriesRemainSameForRecommendedLAOnAttemptingViewAnExample() {
    }

    @Test(groups = { Groups.REGRESSION, Groups.LEARNER }, enabled = true,
            description = "Verify number of tries on recommended learning aids remain same when on completely attempting View An Example")
    public void verifyStateOfRecommendedLAOnClosingPracticeTest() {
        startReport(getTestCaseId(),
                "Verify number of tries on recommended learning aids remain same when on completely attempting View An Example");
        CommonUtil objCommonUtil = new CommonUtil(reportTestObj, APP_LOG);
        GLP_Utilities objRestUtil = new GLP_Utilities(reportTestObj, APP_LOG);
        String learnerUserName = "GLP_Learner_" + getTestCaseId() + "_"
                + objCommonUtil.generateRandomStringOfAlphabets(4);
        try {
            // Create user and subscribe course using corresponding APIs.
            objRestUtil.createLearnerAndSubscribeCourse(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"),
                    ResourceConfigurations
                            .getProperty("consoleUserTypeLearner"),
                    configurationsXlsMap.get("INSTRUCTOR_PRACTICE_USER_NAME"),
                    true);

            // Login to the GLP application
            GLPConsole_LoginPage objProductApplicationConsoleLoginPage = new GLPConsole_LoginPage(
                    reportTestObj, APP_LOG);
            objProductApplicationConsoleLoginPage.login(learnerUserName,
                    ResourceConfigurations.getProperty("consolePassword"));

            GLPLearner_CourseViewPage objGLPLearnerCourseViewPage = new GLPLearner_CourseViewPage(
                    reportTestObj, APP_LOG);
            // Verify CourseTile is Present and navigate to Welcome Learner
            // Screen
            objGLPLearnerCourseViewPage.verifyCourseTilePresent();

            // Navigate to Diagnostic first question
            GLPLearner_CourseHomePage objGLPLearnerCourseHomePage = new GLPLearner_CourseHomePage(
                    reportTestObj, APP_LOG);
            GLPLearner_PracticeTestPage objGLPLearnerPracticeTestPage = new GLPLearner_PracticeTestPage(
                    reportTestObj, APP_LOG);

            // Navigate to diagnostic page
            GLPLearner_DiagnosticTestPage objGLPLearnerDiagnosticTestPage = objGLPLearnerCourseHomePage
                    .navigateToDiagnosticPage();
            // Attempt the diagnostic for the created user
            objGLPLearnerDiagnosticTestPage.attemptAdaptiveDiagnosticTest(0, 0,
                    ResourceConfigurations.getProperty("submitWithoutAttempt"));

            // Navigate to the desired practice Test
            objGLPLearnerPracticeTestPage.navigateToDesiredPracticeTest(
                    ResourceConfigurations.getProperty("module11Text"),
                    ResourceConfigurations
                            .getProperty("subModule11_1AriaLabel"),
                    "PracticeTestFirstLO", "PracticeTestFirstLOPracticeQuiz");

            // Navigate to Recommended Learning Aids screen
            objGLPLearnerPracticeTestPage.attempPracticeTestTillHMST();
            // Verify Progress Bar on HMST Full screen
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "HMSTProgressBar",
                    "Verify Progress bar is displayed on top of Recommended LA window");
            // Verify HMST title on Full screen
            objGLPLearnerPracticeTestPage.verifyElementPresent("HMSTHeader",
                    "Verify the tile of the Recommended Learning Aids window");
            // Verify whether Learning Aids drop down is displayed
            objGLPLearnerPracticeTestPage.verifyElementPresent(
                    "HelpMeAnswerThisDropdown",
                    "Verify Help Me Answer This drop down is displayed");
            // Get the total number of parts of the LA
            int totalParts = objGLPLearnerPracticeTestPage
                    .getNumberOfPartsLearningAids();
            // Navigate to interactive part on HMST full screen
            int interactivePart = objGLPLearnerPracticeTestPage
                    .navigateToInteractiveHMSTPart(totalParts);
            // Get the number of tries for the part
            int originalTries = objGLPLearnerPracticeTestPage
                    .getNumberOfTriesLeftParticularQuestionInHMSTLA(
                            interactivePart);
            // Select the answer for rendered question
            objGLPLearnerPracticeTestPage
                    .attemptInteractivePartinHMSTLA(interactivePart);
            // Verify tries get decreased
            objGLPLearnerPracticeTestPage.verifyExpectedNumberOfTriesForHMST(
                    interactivePart, originalTries, false);

            // Open View An Example Learning Aids
            objGLPLearnerPracticeTestPage
                    .openLearningAids(ResourceConfigurations
                            .getProperty("learningAidsViewAnExample"));
            // Get the total number of parts of the LA
            int totalPartsVAE = objGLPLearnerPracticeTestPage
                    .getNumberOfPartsLearningAids();
            // Attempt all parts of the Learning Aids and click on Close button
            objGLPLearnerPracticeTestPage.attemptStudentInitiatedLA(1,
                    totalPartsVAE, true);
            // Verify Recommended LA screen is displayed
            objGLPLearnerPracticeTestPage.verifyElementPresent("HMSTHeader",
                    "Verify Recommended LA screen is displayed");
            // Fetch number of tries for interactive part
            int reopenTriesCount = objGLPLearnerPracticeTestPage
                    .getNumberOfTriesLeftParticularQuestionInHMSTLA(
                            interactivePart);
            objGLPLearnerPracticeTestPage.verifyExpectedNumberOfTriesForHMST(
                    interactivePart, reopenTriesCount, true);
            // Again attempt the interactive part of HMST
            objGLPLearnerPracticeTestPage
                    .attemptInteractivePartinHMSTLA(interactivePart);
            // Verify the number of tries get decreased
            objGLPLearnerPracticeTestPage.verifyExpectedNumberOfTriesForHMST(
                    interactivePart, reopenTriesCount, false);

        } finally {
            webDriver.close();
            webDriver = null;
        }
    }
}
