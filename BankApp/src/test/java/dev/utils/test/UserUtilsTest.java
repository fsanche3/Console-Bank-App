package dev.utils.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
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

public class UserUtilsTest {
	static User user;
	static UserInput UI;
	Login login;
	SignUp signUp;
	static ConDAO con;

	@BeforeAll
	private static void setUp() {
		user = new User("u", "p");
		UI = new UserInput();
		con = new ConDAO();
	}
	
	@Test
	public void getConnection() {
		// Testing DB Connection
		try {
			Connection connected = con.getConnection();
			Assertions.assertNotNull(connected);
			Assertions.assertTrue(connected.isValid(0));
			connected.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	@Test
	public void addToTransactionsList() {
		// test String add to array list assert it
		String miniRec = "\"Deposited $\"+df.format(amount)+\" into Checkings Account# \"+accountnum+\"\"";
		user.addToList(miniRec);
		List<String> checkTransactions1 = new ArrayList<>();
		checkTransactions1.add(miniRec);
		Assertions.assertEquals(checkTransactions1, user.getList());
	}


	@Test
	public void getuserName() {
		String username = user.getUsername();
		Assertions.assertEquals("u", username);
	}

}
