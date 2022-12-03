package com.revature.ers.services;

import com.revature.ers.daos.ReimbursementDAO;
import com.revature.ers.dtos.requests.NewReimbRequest;
import com.revature.ers.models.Reimbursement;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;

public class ReimbursementServiceTest {
    private ReimbursementService sut;
    private final ReimbursementDAO mockReimbursementDAO = Mockito.mock(ReimbursementDAO.class);

    @Before
    public void init() {
        sut = new ReimbursementService(mockReimbursementDAO);
    }

    @Test
    public void test_isValidAmount_givenValidAmount() {
        // Assert
        double validAmount = 50.00;

        // Act
        boolean condition = sut.isValidAmount(validAmount);

        // Arrange
        assertTrue(condition);
    }

    @Test
    public void test_isValidAmount_givenInvalidAmount() {
        // Assert
        double invalidAmount = 5000.00;

        // Act
        boolean condition = sut.isValidAmount(invalidAmount);

        // Arrange
        assertFalse(condition);
    }

    @Test
    public void test_isEmptyDescription_givenDescription() {
        // Assert
        String description = "hotel";

        // Act
        boolean condition = sut.isEmptyDescription(description);

        // Arrange
        assertFalse(condition);
    }

    @Test
    public void test_isEmptyDescription_givenEmptyDescription() {
        // Assert
        String emptyDescription = "";

        // Act
        boolean condition = sut.isEmptyDescription(emptyDescription);

        // Arrange
        assertTrue(condition);
    }

    @Test
    public void test_submit_persistReimbGivenValidParameters() {
        // Arrange
        ReimbursementService spySut = Mockito.spy(sut);
        double validAmount = 100.00;
        String description = "airplane tickets";
        String authorId = "cb7f61c8-a4bc-452f-94a8-bf0a315819de";

        NewReimbRequest stubbedReq = new NewReimbRequest
                (validAmount, description, UUID.randomUUID().toString(), "b676ad68-485c-43f6-8575-4195e3a050c4");

        // Act
        Reimbursement createdReimb = spySut.submit(stubbedReq, authorId);

        // Assert
        assertNotNull(createdReimb);
        assertNotNull(createdReimb.getId());
        assertEquals(validAmount, createdReimb.getAmount(), 0.1);
        assertNotNull(createdReimb.getSubmitted());
        assertNull(createdReimb.getResolved());
        assertEquals(description, createdReimb.getDescription());
        assertNotNull(createdReimb.getPaymentId());
        assertEquals(authorId, createdReimb.getAuthorId());
        assertNull(createdReimb.getResolverId());
        assertEquals("4eac4123-f552-4ea5-ab86-3ca7715e6f20", createdReimb.getStatusId());
        assertNotNull(createdReimb.getTypeId());
        assertNotEquals("", createdReimb.getTypeId());
        Mockito.verify(mockReimbursementDAO, Mockito.times(1)).save(createdReimb);
    }

    @Test
    public void test_approve_givenValidId() {
        // Arrange
        String validId = UUID.randomUUID().toString();
        Reimbursement stubbedReimb = new Reimbursement
                (validId, 100.00, new Date(), null, "wine", null,
                        "cb7f61c8-a4bc-452f-94a8-bf0a315819de", null,
                        "4eac4123-f552-4ea5-ab86-3ca7715e6f20", "1c4f7cdd-45b3-43b6-97af-31d6aa9a815b");

        String resolverId = "cb7f61c8-a4bc-452f-94a8-bf0a315819de";
        Mockito.when(mockReimbursementDAO.findById(validId)).thenReturn(stubbedReimb);

        // Act
        Reimbursement condition = sut.approve(validId, resolverId);

        // Assert
        assertNotNull(condition.getResolved());
        assertEquals(resolverId, condition.getResolverId());
        assertEquals("e601bb35-d2b6-4279-985f-3302889ed721", condition.getStatusId());
    }

    @Test
    public void test_deny_givenValidId() {
        // Arrange
        String validId = UUID.randomUUID().toString();
        Reimbursement stubbedReimb = new Reimbursement
                (validId, 100.00, new Date(), null, "wine", null,
                        "cb7f61c8-a4bc-452f-94a8-bf0a315819de", null,
                        "4eac4123-f552-4ea5-ab86-3ca7715e6f20", "1c4f7cdd-45b3-43b6-97af-31d6aa9a815b");

        String resolverId = "cb7f61c8-a4bc-452f-94a8-bf0a315819de";
        Mockito.when(mockReimbursementDAO.findById(validId)).thenReturn(stubbedReimb);

        // Act
        Reimbursement condition = sut.deny(validId, resolverId);

        // Assert
        assertNotNull(condition.getResolved());
        assertEquals(resolverId, condition.getResolverId());
        assertEquals("02f8e3b9-88a1-4259-b133-d8b5ba2861fb", condition.getStatusId());
    }

    @Test
    public void test_getAllReimbs_givenReimbs() {
        // Arrange
        Reimbursement stubbedReimb1 = new Reimbursement();
        Reimbursement stubbedReimb2 = new Reimbursement();
        Reimbursement stubbedReimb3 = new Reimbursement();
        List<Reimbursement> stubbedReimbs = Arrays.asList(stubbedReimb1, stubbedReimb2, stubbedReimb3);
        Mockito.when(mockReimbursementDAO.findAll()).thenReturn(stubbedReimbs);

        // Act
        List<Reimbursement> condition = sut.getAllReimbs();

        // Assert
        assertFalse(condition.isEmpty());
    }

    @Test
    public void test_getAllReimbs_givenNoReimbs() {
        // Arrange
        List <Reimbursement> stubbedReimbs = new ArrayList<>();
        Mockito.when(mockReimbursementDAO.findAll()).thenReturn(stubbedReimbs);

        // Act
        List<Reimbursement> condition = sut.getAllReimbs();

        // Assert
        assertTrue(condition.isEmpty());
    }

    @Test
    public void test_getAllReimbsByAuthorId_givenValidAuthorId() {
        // Arrange
        String validAuthorId = "63d606ef-7c41-4e27-955b-0774aa868625";
        Reimbursement stubbedReimb1 = new Reimbursement();
        Reimbursement stubbedReimb2 = new Reimbursement();
        Reimbursement stubbedReimb3 = new Reimbursement();
        List<Reimbursement> stubbedReimbs = Arrays.asList(stubbedReimb1, stubbedReimb2, stubbedReimb3);
        Mockito.when(mockReimbursementDAO.findAllByAuthorId(validAuthorId)).thenReturn(stubbedReimbs);

        // Act
        List<Reimbursement> condition = sut.getAllReimbsByAuthorId(validAuthorId);

        // Assert
        assertFalse(condition.isEmpty());
    }

    @Test
    public void test_getAllReimbsByAuthorId_givenInvalidAuthorId() {
        // Arrange
        String invalidAuthorId = "9ce9d541-b8c8-4bc8-8990-153927227074";
        List<Reimbursement> stubbedReimbs = new ArrayList<>();
        Mockito.when(mockReimbursementDAO.findAllByAuthorId(invalidAuthorId)).thenReturn(stubbedReimbs);

        // Act
        List<Reimbursement> condition = sut.getAllReimbsByAuthorId(invalidAuthorId);

        // Assert
        assertTrue(condition.isEmpty());
    }

    @Test
    public void test_getAllReimbsByResolverId_givenValidResolverId() {
        // Arrange
        String validResolverId = "cb7f61c8-a4bc-452f-94a8-bf0a315819de";
        Reimbursement stubbedReimb1 = new Reimbursement();
        Reimbursement stubbedReimb2 = new Reimbursement();
        Reimbursement stubbedReimb3 = new Reimbursement();
        List<Reimbursement> stubbedReimbs = Arrays.asList(stubbedReimb1, stubbedReimb2, stubbedReimb3);
        Mockito.when(mockReimbursementDAO.findAllByResolverId(validResolverId)).thenReturn(stubbedReimbs);

        // Act
        List<Reimbursement> condition = sut.getAllReimbsByResolverId(validResolverId);

        // Assert
        assertFalse(condition.isEmpty());
    }

    @Test
    public void test_getAllReimbsByResolverId_givenInvalidResolverId() {
        // Arrange
        String invalidResolverId = "9ce9d541-b8c8-4bc8-8990-153927227074";
        List<Reimbursement> stubbedReimbs = new ArrayList<>();
        Mockito.when(mockReimbursementDAO.findAllByResolverId(invalidResolverId)).thenReturn(stubbedReimbs);

        // Act
        List<Reimbursement> condition = sut.getAllReimbsByResolverId(invalidResolverId);

        // Assert
        assertTrue(condition.isEmpty());
    }

    @Test
    public void test_getAllReimbsByStatusId_givenValidStatusId() {
        // Arrange
        String validStatusId = "4eac4123-f552-4ea5-ab86-3ca7715e6f20";
        Reimbursement stubbedReimb1 = new Reimbursement();
        Reimbursement stubbedReimb2 = new Reimbursement();
        Reimbursement stubbedReimb3 = new Reimbursement();
        List<Reimbursement> stubbedReimbs = Arrays.asList(stubbedReimb1, stubbedReimb2, stubbedReimb3);
        Mockito.when(mockReimbursementDAO.findAllByStatusId(validStatusId)).thenReturn(stubbedReimbs);

        // Act
        List<Reimbursement> condition = sut.getAllReimbsByStatusId(validStatusId);

        // Assert
        assertFalse(condition.isEmpty());
    }

    @Test
    public void test_getAllReimbsByResolverId_givenInvalidStatusId() {
        // Arrange
        String invalidStatusId = "";
        List<Reimbursement> stubbedReimbs = new ArrayList<>();
        Mockito.when(mockReimbursementDAO.findAllByStatusId(invalidStatusId)).thenReturn(stubbedReimbs);

        // Act
        List<Reimbursement> condition = sut.getAllReimbsByStatusId(invalidStatusId);

        // Assert
        assertTrue(condition.isEmpty());
    }

    @Test
    public void test_getAllReimbsByTypeId_givenValidTypeId() {
        // Arrange
        String validTypeId = "38410f2f-6a27-41a6-a2c8-dcf5fe86ede1";
        Reimbursement stubbedReimb1 = new Reimbursement();
        Reimbursement stubbedReimb2 = new Reimbursement();
        Reimbursement stubbedReimb3 = new Reimbursement();
        List<Reimbursement> stubbedReimbs = Arrays.asList(stubbedReimb1, stubbedReimb2, stubbedReimb3);
        Mockito.when(mockReimbursementDAO.findAllByTypeId(validTypeId)).thenReturn(stubbedReimbs);

        // Act
        List<Reimbursement> condition = sut.getAllReimbsByTypeId(validTypeId);

        // Assert
        assertFalse(condition.isEmpty());
    }

    @Test
    public void test_getAllReimbsByTypeId_givenInvalidTypeId() {
        // Arrange
        String invalidTypeId = "";
        List<Reimbursement> stubbedReimbs = new ArrayList<>();
        Mockito.when(mockReimbursementDAO.findAllByTypeId(invalidTypeId)).thenReturn(stubbedReimbs);

        // Act
        List<Reimbursement> condition = sut.getAllReimbsByTypeId(invalidTypeId);

        // Assert
        assertTrue(condition.isEmpty());
    }
}