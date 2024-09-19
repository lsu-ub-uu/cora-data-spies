/*
 * Copyright 2022 Olov McKie
 * Copyright 2022 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.uu.ub.cora.data.spies;

import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.data.Data;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;
import se.uu.ub.cora.testutils.spies.MCRSpy;

public class DataListSpyTest {
	private static final String ADD_CALL = "addCall";
	private static final String ADD_CALL_AND_RETURN_FROM_MRV = "addCallAndReturnFromMRV";
	DataListSpy dataList;
	private MCRSpy MCRSpy;
	private MethodCallRecorder mcrForSpy;

	@BeforeMethod
	public void beforeMethod() {
		MCRSpy = new MCRSpy();
		mcrForSpy = MCRSpy.MCR;
		dataList = new DataListSpy();
	}

	@Test
	public void testMakeSureSpyHelpersAreSetUp() throws Exception {
		assertTrue(dataList.MCR instanceof MethodCallRecorder);
		assertTrue(dataList.MRV instanceof MethodReturnValues);
		assertSame(dataList.MCR.onlyForTestGetMRV(), dataList.MRV);
	}

	@Test
	public void testDefaultGetFromNo() throws Exception {
		assertTrue(dataList.getFromNo() instanceof String);
	}

	@Test
	public void testGetFromNo() throws Exception {
		dataList.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataList.getFromNo();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetToNo() throws Exception {
		assertTrue(dataList.getToNo() instanceof String);
	}

	@Test
	public void testGetToNo() throws Exception {
		dataList.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataList.getToNo();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetTotalNumberOfTypeInStorage() throws Exception {
		assertTrue(dataList.getTotalNumberOfTypeInStorage() instanceof String);
	}

	@Test
	public void testGetTotalNumberOfTypeInStorage() throws Exception {
		dataList.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataList.getTotalNumberOfTypeInStorage();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test

	public void testDefaultGetContainDataOfType() throws Exception {
		assertTrue(dataList.getContainDataOfType() instanceof String);
	}

	@Test
	public void testGetContainDataOfType() throws Exception {
		dataList.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataList.getContainDataOfType();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test

	public void testDefaultGetDataList() throws Exception {
		assertTrue(dataList.getDataList() instanceof List);
	}

	@Test
	public void testGetDataList() throws Exception {
		dataList.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<Data>::new);

		List<Data> returnedValue = dataList.getDataList();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testAddData() throws Exception {
		Data data = new DataRecordSpy();
		dataList.MCR = MCRSpy;

		dataList.addData(data);

		mcrForSpy.assertParameter(ADD_CALL, 0, "data", data);
	}

	@Test
	public void testSetFromNo() throws Exception {
		String postion = "";
		dataList.MCR = MCRSpy;

		dataList.setFromNo(postion);

		mcrForSpy.assertParameter(ADD_CALL, 0, "position", postion);
	}

	@Test
	public void testSetToNo() throws Exception {
		String postion = "";
		dataList.MCR = MCRSpy;

		dataList.setToNo(postion);

		mcrForSpy.assertParameter(ADD_CALL, 0, "position", postion);
	}

	@Test
	public void testSetTotalNo() throws Exception {
		String totalNumber = "";
		dataList.MCR = MCRSpy;

		dataList.setTotalNo(totalNumber);

		mcrForSpy.assertParameter(ADD_CALL, 0, "totalNumber", totalNumber);
	}

}
