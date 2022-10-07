/*
 * Copyright 2022 Olov McKie
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

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.data.DataAtomic;
import se.uu.ub.cora.data.DataAttribute;
import se.uu.ub.cora.data.DataChild;
import se.uu.ub.cora.data.DataChildFilter;
import se.uu.ub.cora.data.DataGroup;
import se.uu.ub.cora.data.spies.DataAtomicSpy;
import se.uu.ub.cora.data.spies.DataAttributeSpy;
import se.uu.ub.cora.data.spies.DataChildFilterSpy;
import se.uu.ub.cora.data.spies.DataChildSpy;
import se.uu.ub.cora.data.spies.DataGroupSpy;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;
import se.uu.ub.cora.testutils.spies.MCRSpy;

public class DataGroupSpyTest {
	private static final String ADD_CALL = "addCall";
	private static final String ADD_CALL_AND_RETURN_FROM_MRV = "addCallAndReturnFromMRV";
	DataGroupSpy dataGroup;
	private MCRSpy MCRSpy;
	private MethodCallRecorder mcrForSpy;

	@BeforeMethod
	public void beforeMethod() {
		MCRSpy = new MCRSpy();
		mcrForSpy = MCRSpy.MCR;
		dataGroup = new DataGroupSpy();
	}

	@Test
	public void testMakeSureSpyHelpersAreSetUp() throws Exception {
		assertTrue(dataGroup.MCR instanceof MethodCallRecorder);
		assertTrue(dataGroup.MRV instanceof MethodReturnValues);
		assertSame(dataGroup.MCR.onlyForTestGetMRV(), dataGroup.MRV);
	}

	@Test
	public void testAddChildNoSpy() throws Exception {
		DataChildSpy dataChild = new DataChildSpy();
		dataGroup.addChild(dataChild);

		dataGroup.MCR.assertParameter("addChild", 0, "dataChild", dataChild);
	}

	@Test
	public void testSetRepeatId() throws Exception {
		dataGroup.MCR = MCRSpy;

		dataGroup.setRepeatId("repeat1");

		mcrForSpy.assertParameter(ADD_CALL, 0, "repeatId", "repeat1");
	}

	@Test
	public void testDefaultGetRepeatId() throws Exception {
		assertTrue(dataGroup.getRepeatId() instanceof String);
	}

	@Test
	public void testGetRepeatId() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataGroup.getRepeatId();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testAddAttributeByIdWithValue() throws Exception {
		dataGroup.MCR = MCRSpy;

		dataGroup.addAttributeByIdWithValue("attribId", "attribValue");

		mcrForSpy.assertParameter(ADD_CALL, 0, "nameInData", "attribId");
		mcrForSpy.assertParameter(ADD_CALL, 0, "value", "attribValue");
	}

	@Test
	public void testDefaultHasAttributes() throws Exception {
		assertFalse(dataGroup.hasAttributes());
	}

	@Test
	public void testHasAttributes() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> true);

		boolean retunedValue = dataGroup.hasAttributes();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAttribute() throws Exception {
		assertTrue(dataGroup.getAttribute("nameInData") instanceof DataAttribute);
	}

	@Test
	public void testGetAttribute() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				DataAttributeSpy::new);

		var returnedValue = dataGroup.getAttribute("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetAttributes() throws Exception {
		assertTrue(dataGroup.getAttributes() instanceof Collection<DataAttribute>);
	}

	@Test
	public void testGetAttributes() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAttribute>::new);

		var returnedValue = dataGroup.getAttributes();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetNameInData() throws Exception {
		assertTrue(dataGroup.getNameInData() instanceof String);
	}

	@Test
	public void testGetNameInData() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataGroup.getNameInData();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultHasChildren() throws Exception {
		assertTrue(dataGroup.hasChildren());
	}

	@Test
	public void testHasChildren() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataGroup.hasChildren();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultContainsChildWithNameInData() throws Exception {
		assertFalse(dataGroup.containsChildWithNameInData("nameInData"));
	}

	@Test
	public void testContainsChildWithNameInData() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataGroup.containsChildWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testAddChild() throws Exception {
		DataChildSpy dataChild = new DataChildSpy();
		dataGroup.MCR = MCRSpy;

		dataGroup.addChild(dataChild);

		mcrForSpy.assertParameter(ADD_CALL, 0, "dataChild", dataChild);
	}

	@Test
	public void testAddChildren() throws Exception {
		Collection<DataChild> children = new ArrayList<>();
		dataGroup.MCR = MCRSpy;

		dataGroup.addChildren(children);

		mcrForSpy.assertParameter(ADD_CALL, 0, "dataChildren", children);
	}

	@Test
	public void testDefaultGetChildren() throws Exception {
		assertTrue(dataGroup.getChildren() instanceof List<DataChild>);
	}

	@Test
	public void testGetChildren() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChild>::new);

		List<DataChild> retunedValue = dataGroup.getChildren();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllChildrenWithNameInData() throws Exception {
		assertTrue(dataGroup.getAllChildrenWithNameInData("nameInData") instanceof List<DataChild>);
	}

	@Test
	public void testGetAllChildrenWithNameInData() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChild>::new);

		List<DataChild> retunedValue = dataGroup.getAllChildrenWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllChildrenWithNameInDataAndAttributes() throws Exception {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataGroup.getAllChildrenWithNameInDataAndAttributes("nameInData",
				dataAttribute) instanceof List<DataChild>);
	}

	@Test
	public void testGetAllChildrenWithNameInDataAndAttributes() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChild>::new);
		DataAttribute dataAttribute = new DataAttributeSpy();

		List<DataChild> retunedValue = dataGroup
				.getAllChildrenWithNameInDataAndAttributes("nameInData", dataAttribute);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		Object[] returnValue = (Object[]) mcrForSpy
				.getValueForMethodNameAndCallNumberAndParameterName(ADD_CALL_AND_RETURN_FROM_MRV, 0,
						"childAttributes");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childAttributes", returnValue);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetFirstChildWithNameInData() throws Exception {
		assertTrue(dataGroup.getFirstChildWithNameInData("nameInData") instanceof DataChildSpy);
	}

	@Test
	public void testGetFirstChildWithNameInData() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataChildSpy::new);

		DataChild retunedValue = dataGroup.getFirstChildWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetFirstAtomicValueWithNameInData() throws Exception {
		assertTrue(dataGroup.getFirstAtomicValueWithNameInData("nameInData") instanceof String);
	}

	@Test
	public void testGetFirstAtomicValueWithNameInData() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String retunedValue = dataGroup.getFirstAtomicValueWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	// TODO
	@Test
	public void testDefaultGetFirstDataAtomicWithNameInData() throws Exception {
		assertTrue(
				dataGroup.getFirstDataAtomicWithNameInData("nameInData") instanceof DataAtomicSpy);
	}

	@Test
	public void testGetFirstDataAtomicWithNameInData() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataAtomicSpy::new);

		DataChild retunedValue = dataGroup.getFirstDataAtomicWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllDataAtomicsWithNameInData() throws Exception {
		assertTrue(dataGroup
				.getAllDataAtomicsWithNameInData("nameInData") instanceof List<DataAtomic>);
	}

	@Test
	public void testGetAllDataAtomicsWithNameInData() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);

		List<DataAtomic> retunedValue = dataGroup.getAllDataAtomicsWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllDataAtomicsWithNameInDataAndAttributes() throws Exception {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataGroup.getAllDataAtomicsWithNameInDataAndAttributes("nameInData",
				dataAttribute) instanceof List<DataAtomic>);
	}

	@Test
	public void testGetAllDataAtomicsWithNameInDataAndAttributes() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);
		DataAttribute dataAttribute = new DataAttributeSpy();

		List<DataAtomic> retunedValue = (List<DataAtomic>) dataGroup
				.getAllDataAtomicsWithNameInDataAndAttributes("nameInData", dataAttribute);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		Object[] returnValue = (Object[]) mcrForSpy
				.getValueForMethodNameAndCallNumberAndParameterName(ADD_CALL_AND_RETURN_FROM_MRV, 0,
						"childAttributes");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childAttributes", returnValue);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetFirstGroupWithNameInData() throws Exception {
		assertTrue(dataGroup.getFirstGroupWithNameInData("nameInData") instanceof DataGroupSpy);
	}

	@Test
	public void testGetFirstGroupWithNameInData() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataGroupSpy::new);

		DataGroup retunedValue = dataGroup.getFirstGroupWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllGroupsWithNameInData() throws Exception {
		assertTrue(dataGroup
				.getAllGroupsWithNameInData("nameInData") instanceof Collection<DataGroup>);
	}

	@Test
	public void testGetAllGroupsWithNameInData() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);

		List<DataGroup> retunedValue = dataGroup.getAllGroupsWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllGroupsWithNameInDataAndAttributes() throws Exception {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataGroup.getAllGroupsWithNameInDataAndAttributes("nameInData",
				dataAttribute) instanceof Collection<DataGroup>);
	}

	@Test
	public void testGetAllGroupsWithNameInDataAndAttributes() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);
		DataAttribute dataAttribute = new DataAttributeSpy();

		Collection<DataGroup> retunedValue = dataGroup
				.getAllGroupsWithNameInDataAndAttributes("nameInData", dataAttribute);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		Object[] returnValue = (Object[]) mcrForSpy
				.getValueForMethodNameAndCallNumberAndParameterName(ADD_CALL_AND_RETURN_FROM_MRV, 0,
						"childAttributes");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childAttributes", returnValue);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultRemoveFirstChildWithNameInData() throws Exception {
		assertTrue(dataGroup.removeFirstChildWithNameInData("nameInData"));
	}

	@Test
	public void testRemoveFirstChildWithNameInData() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataGroup.removeFirstChildWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultRemoveAllChildrenWithNameInData() throws Exception {
		assertTrue(dataGroup.removeAllChildrenWithNameInData("nameInData"));
	}

	@Test
	public void testRemoveAllChildrenWithNameInData() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataGroup.removeAllChildrenWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultRemoveAllChildrenWithNameInDataAndAttributes() throws Exception {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataGroup.removeAllChildrenWithNameInDataAndAttributes("nameInData",
				dataAttribute));
	}

	@Test
	public void testRemoveAllChildrenWithNameInDataAndAttributes() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);
		DataAttribute dataAttribute = new DataAttributeSpy();

		boolean retunedValue = dataGroup.removeAllChildrenWithNameInDataAndAttributes("nameInData",
				dataAttribute);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		Object[] returnValue = (Object[]) mcrForSpy
				.getValueForMethodNameAndCallNumberAndParameterName(ADD_CALL_AND_RETURN_FROM_MRV, 0,
						"childAttributes");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childAttributes", returnValue);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllChildrenMatchingFilter() throws Exception {
		DataChildFilter childFilter = new DataChildFilterSpy();
		assertTrue(dataGroup.getAllChildrenMatchingFilter(childFilter) instanceof List<DataChild>);
	}

	@Test
	public void testGetAllChildrenMatchingFilter() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChildFilter>::new);
		DataChildFilter childFilter = new DataChildFilterSpy();

		List<DataChild> retunedValue = dataGroup.getAllChildrenMatchingFilter(childFilter);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childFilter", childFilter);

		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultRemoveAllChildrenMatchingFilter() throws Exception {
		DataChildFilter childFilter = new DataChildFilterSpy();
		assertTrue(dataGroup.removeAllChildrenMatchingFilter(childFilter));
	}

	@Test
	public void testRemoveAllChildrenMatchingFilter() throws Exception {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);
		DataChildFilter childFilter = new DataChildFilterSpy();

		boolean retunedValue = dataGroup.removeAllChildrenMatchingFilter(childFilter);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childFilter", childFilter);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

}
