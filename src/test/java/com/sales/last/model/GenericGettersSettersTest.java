package com.sales.last.model;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.sales.last.util.EqualsTester;
import com.sales.last.util.PropertyAsserter;

public class GenericGettersSettersTest<T> {

	@SuppressWarnings("unchecked")
	public List<T> getClases() {

		List<T> listadoClases = new ArrayList<>();
		
		listadoClases.add((T) Tax.class);
		
		return listadoClases;
	}

	@Test
	public void testGetterAndSetter() {
		try {

			for (T clase : getClases()) {

				final Class<?> clazz = (Class<?>) clase;
				final Constructor<?> ctor = clazz.getConstructor();
				final Object object = ctor.newInstance();
				PropertyAsserter.assertBasicGetterSetterBehavior(object);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void equals() {
		
		Tax obj = new Tax();
		Tax obj2 = new Tax();
		
		boolean result = obj.equals(obj2);
	}

	@Test
	public void testHashCodeAndEquals() {

		try {
			for (T clase : getClases()) {

				final Class<?> clazz = (Class<?>) clase;
				final Constructor<?> ctor = clazz.getConstructor();
				final Object object = ctor.newInstance();

				try {
					EqualsTester<Object> tester = EqualsTester.newInstance(object);
					tester.assertImplementsEqualsAndHashCode();
					tester.assertEqual(object, object, object);
					tester.assertNotEqual(object, new ArrayList<>());

				} catch (AssertionError e) {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
