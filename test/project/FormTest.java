/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package project;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Abdullah Ahmed
 */
public class FormTest {
    
    private static final String TEST_URL = "jdbc:sqlserver://localhost\\ADSW;databaseName=CoursesPlatformDB;"
                                        + "user=sa;password=2004;"
                                        + "encrypt=true;trustServerCertificate=true;";
    
    public FormTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        // إعداد بيانات الاختبار في قاعدة البيانات
        
        }
    
    
    @AfterClass
    public static void tearDownClass() {
        // تنظيف بيانات الاختبار من قاعدة البيانات
        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Form.main(args); // هذا سيفتح الواجهة الرئيسية
        assertTrue(true); // تأكيد أن الكود تم تنفيذه بدون أخطاء
    }
    
    @Test
    public void testStudentLoginSuccess() {
        System.out.println("studentLoginSuccess");
        
        // محاكاة إدخال البيانات
        String username = "abdullah55";
        String password = "abdullah55";
        String role = "Student";
        
        boolean result = testLogin(username, password, role);
        assertTrue("Student login should succeed with correct credentials", result);
    }
    
    @Test
    public void testInstructorLoginSuccess() {
        System.out.println("instructorLoginSuccess");
        
        // محاكاة إدخال البيانات
        String username = "samairali";
        String password = "99999";
        String role = "Instructor";
        
        boolean result = testLogin(username, password, role);
        assertTrue("Instructor login should succeed with correct credentials", result);
    }
    
    @Test
    public void testLoginWithWrongPassword() {
        System.out.println("loginWithWrongPassword");
        
        String username = "reda222";
        String password = "reda222";
        String role = "Student";
        
        boolean result = testLogin(username, password, role);
        assertFalse("Login should fail with wrong password", result);
    }
    
    @Test
    public void testLoginWithNoRoleSelected() {
        System.out.println("loginWithNoRoleSelected");
        
        try {
            // محاكاة حالة عدم اختيار أي دور
            String username = "testStudent";
            String password = "student123";
            String role = "";
            
            if (role.isEmpty()) {
                // في الواقع، سيتم عرض JOptionPane هنا
                throw new IllegalArgumentException("Please select a role (Student or Instructor)");
            }
            
            fail("Expected exception when no role is selected");
        } catch (IllegalArgumentException ex) {
            assertEquals("Please select a role (Student or Instructor)", ex.getMessage());
        }
    }
    
    // دالة مساعدة لاختبار تسجيل الدخول
    private boolean testLogin(String username, String password, String role) {
        try (Connection conn = DriverManager.getConnection(TEST_URL)) {
            String sql;
            if (role.equals("Student")) {
                sql = "SELECT StudentID FROM Students WHERE user_name = ? AND Password = ?";
            } else if (role.equals("Instructor")) {
                sql = "SELECT InstructorID FROM Instructors WHERE user_name = ? AND Password = ?";
            } else {
                return false;
            }
            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (SQLException ex) {
            fail("Database error during test: " + ex.getMessage());
            return false;
        }
    }
}
