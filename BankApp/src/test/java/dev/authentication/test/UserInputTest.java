package dev.authentication.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.bankapp.authentication.Login;
import dev.bankapp.authentication.SignUp;
import dev.bankapp.authentication.User;
import dev.bankapp.authentication.UserInput;
import dev.bankapp.utils.*;

@TestInstance(Lifecycle.PER_CLASS)

public class UserInputTest {
	User user = new User("username","password");
	UserInput UI = new UserInput();
	Login login;
	SignUp signUp;
	
	
	// Before all is used once before all test for 
	//things like file and DB connection
	
	@BeforeAll
	public static void setUp() {
		// Instance instantiation

	}
	
	@Test
	public void addToTransactionsList(){
		// test String add to array list assert it
		String miniRec = "\"Deposited $\"+df.format(amount)+\" into Checkings Account# \"+accountnum+\"\"";
		user.addToList(miniRec);
		List<String> checkTransactions1 = new ArrayList<>();
		checkTransactions1.add(miniRec);
			Assertions.assertEquals(checkTransactions1, user.getList());
	}
	
	@Test
	public void getReciever(){
		// Gets the recipients account# --> checks if in database it is for this test (rsNext) -- >returns number 
		int accNum = 2;
		boolean rsNext = false;
	}
	
	
	@AfterAll
	public void  afterAll() {

	}
	
	
	
	
	
	
	
	
	

}
