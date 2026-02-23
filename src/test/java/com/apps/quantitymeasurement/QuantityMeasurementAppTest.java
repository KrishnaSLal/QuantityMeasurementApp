package com.apps.quantitymeasurement;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.apps.quantitymeasurement.QuantityMeasurementApp.*;


public class QuantityMeasurementAppTest {
	@Test
	public void testFeetEquality_SameValue() {
		Feet f1 = new Feet(1.0);
		Feet f2 = new Feet(1.0);
		assertTrue(f1.equals(f2));
	}

	@Test
	public void testFeetEquality_DifferentValue() {
		Feet f1 = new Feet(1.0);
		Feet f2 = new Feet(2.0);
		assertFalse(f1.equals(f2));
	}

	@Test
	public void testFeetEquality_NullComparison() {
		Feet f1 = new Feet(1.0);
		assertFalse(f1.equals(null));
	}

	@Test
	public void testFeetEquality_DifferentClass() {
		Feet f1 = new Feet(1.0);
		String other = "NotFeet";
		assertFalse(f1.equals(other));
	}

	@Test
	public void testFeetEquality_SameReference() {
		Feet f1 = new Feet(1.0);
		assertTrue(f1.equals(f1));
	}
	 @Test
	    public void testInchesEquality_SameValue() {
	    	Inches i1 = new Inches(5.0);
	    	Inches i2 = new Inches(5.0);
	    	
	    	assertTrue(i1.equals(i2));
	    }
	    
	    @Test
	    public void testInchesEquality_DiffrentValue() {
	    	Inches i1 = new Inches(5.0);
	    	Inches i2 = new Inches(6.0);
	    	
	    	assertFalse(i1.equals(i2));
	    }
	    
	    @Test
	    public void testInchesEquality_NullComparison() {
	    	Inches inch = new Inches(5.0);
	    	
	    	assertFalse(inch.equals(null));
	    }
	    
	    @Test
	    public void testInchesEquality_DiffrentClass() {
	    	Inches inch = new Inches(5.0);
	    	
	    	assertFalse(inch.equals("1"));
	    }
	    
	    @Test
	    public void testInchesEquality_SameReference() {
	    	Inches inch = new Inches(5.0);
	    	
	    	assertTrue(inch.equals(inch));
	    }
	}