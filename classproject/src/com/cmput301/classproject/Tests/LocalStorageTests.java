package com.cmput301.classproject.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cmput301.classproject.Model.LocalStorage;

public class LocalStorageTests {

	LocalStorage localStorage = LocalStorage.getInstance();
	
	@Test
	public void testCreateLocalStorage() {
		assertTrue(localStorage != null);
	}
	
	@Test
	public void testGetTasks(){
		assertTrue(localStorage.load().size() > 0);
	}

}
