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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.data.DataAtomic;
import se.uu.ub.cora.data.DataAttribute;
import se.uu.ub.cora.data.DataChild;
import se.uu.ub.cora.data.DataChildFilter;
import se.uu.ub.cora.data.DataGroup;
import se.uu.ub.cora.data.DataRecordLink;
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
	public void testMakeSureSpyHelpersAreSetUp() {
		assertTrue(dataGroup.MCR instanceof MethodCallRecorder);
		assertTrue(dataGroup.MRV instanceof MethodReturnValues);
		assertSame(dataGroup.MCR.onlyForTestGetMRV(), dataGroup.MRV);
	}

	@Test
	public void testAddChildNoSpy() {
		DataChildSpy dataChild = new DataChildSpy();
		dataGroup.addChild(dataChild);

		dataGroup.MCR.assertParameter("addChild", 0, "dataChild", dataChild);
	}

	@Test
	public void testSetRepeatId() {
		dataGroup.MCR = MCRSpy;

		dataGroup.setRepeatId("repeat1");

		mcrForSpy.assertParameter(ADD_CALL, 0, "repeatId", "repeat1");
	}

	@Test
	public void testDefaultGetRepeatId() {
		assertTrue(dataGroup.getRepeatId() instanceof String);
	}

	@Test
	public void testDefaultHasRepeatId() {
		assertFalse(dataGroup.hasRepeatId());
	}

	@Test
	public void testHasRepeatId() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, () -> true);

		var returnedValue = dataGroup.hasRepeatId();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testGetRepeatId() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataGroup.getRepeatId();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testAddAttributeByIdWithValue() {
		dataGroup.MCR = MCRSpy;

		dataGroup.addAttributeByIdWithValue("attribId", "attribValue");

		mcrForSpy.assertParameter(ADD_CALL, 0, "nameInData", "attribId");
		mcrForSpy.assertParameter(ADD_CALL, 0, "value", "attribValue");
	}

	@Test
	public void testDefaultHasAttributes() {
		assertFalse(dataGroup.hasAttributes());
	}

	@Test
	public void testHasAttributes() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> true);

		boolean retunedValue = dataGroup.hasAttributes();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAttribute() {
		assertTrue(dataGroup.getAttribute("nameInData") instanceof DataAttribute);
	}

	@Test
	public void testGetAttribute() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				DataAttributeSpy::new);

		var returnedValue = dataGroup.getAttribute("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetAttributes() {
		assertTrue(dataGroup.getAttributes() instanceof Collection<DataAttribute>);
	}

	@Test
	public void testGetAttributes() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAttribute>::new);

		var returnedValue = dataGroup.getAttributes();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetNameInData() {
		assertTrue(dataGroup.getNameInData() instanceof String);
	}

	@Test
	public void testGetNameInData() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataGroup.getNameInData();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultHasChildren() {
		assertTrue(dataGroup.hasChildren());
	}

	@Test
	public void testHasChildren() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataGroup.hasChildren();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultContainsChildWithNameInData() {
		assertFalse(dataGroup.containsChildWithNameInData("nameInData"));
	}

	@Test
	public void testContainsChildWithNameInData() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataGroup.containsChildWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testAddChild() {
		DataChildSpy dataChild = new DataChildSpy();
		dataGroup.MCR = MCRSpy;

		dataGroup.addChild(dataChild);

		mcrForSpy.assertParameter(ADD_CALL, 0, "dataChild", dataChild);
	}

	@Test
	public void testAddChildren() {
		Collection<DataChild> children = new ArrayList<>();
		dataGroup.MCR = MCRSpy;

		dataGroup.addChildren(children);

		mcrForSpy.assertParameter(ADD_CALL, 0, "dataChildren", children);
	}

	@Test
	public void testDefaultGetChildren() {
		assertTrue(dataGroup.getChildren() instanceof List<DataChild>);
	}

	@Test
	public void testGetChildren() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChild>::new);

		List<DataChild> retunedValue = dataGroup.getChildren();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllChildrenWithNameInData() {
		assertTrue(dataGroup.getAllChildrenWithNameInData("nameInData") instanceof List<DataChild>);
	}

	@Test
	public void testGetAllChildrenWithNameInData() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChild>::new);

		List<DataChild> retunedValue = dataGroup.getAllChildrenWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllChildrenWithNameInDataAndAttributes() {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataGroup.getAllChildrenWithNameInDataAndAttributes("nameInData",
				dataAttribute) instanceof List<DataChild>);
	}

	@Test
	public void testGetAllChildrenWithNameInDataAndAttributes() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChild>::new);
		DataAttribute dataAttribute = new DataAttributeSpy();

		List<DataChild> retunedValue = dataGroup
				.getAllChildrenWithNameInDataAndAttributes("nameInData", dataAttribute);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		Object[] returnValue = (Object[]) mcrForSpy.getParameterForMethodAndCallNumberAndParameter(
				ADD_CALL_AND_RETURN_FROM_MRV, 0, "childAttributes");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childAttributes", returnValue);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetFirstChildWithNameInData() {
		assertTrue(dataGroup.getFirstChildWithNameInData("nameInData") instanceof DataChildSpy);
	}

	@Test
	public void testGetFirstChildWithNameInData() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataChildSpy::new);

		DataChild retunedValue = dataGroup.getFirstChildWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetFirstAtomicValueWithNameInData() {
		assertTrue(dataGroup.getFirstAtomicValueWithNameInData("nameInData") instanceof String);
	}

	@Test
	public void testGetFirstAtomicValueWithNameInData() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String retunedValue = dataGroup.getFirstAtomicValueWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	// TODO
	@Test
	public void testDefaultGetFirstDataAtomicWithNameInData() {
		assertTrue(
				dataGroup.getFirstDataAtomicWithNameInData("nameInData") instanceof DataAtomicSpy);
	}

	@Test
	public void testGetFirstDataAtomicWithNameInData() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataAtomicSpy::new);

		DataChild retunedValue = dataGroup.getFirstDataAtomicWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllDataAtomicsWithNameInData() {
		assertTrue(dataGroup
				.getAllDataAtomicsWithNameInData("nameInData") instanceof List<DataAtomic>);
	}

	@Test
	public void testGetAllDataAtomicsWithNameInData() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);

		List<DataAtomic> retunedValue = dataGroup.getAllDataAtomicsWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllDataAtomicsWithNameInDataAndAttributes() {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataGroup.getAllDataAtomicsWithNameInDataAndAttributes("nameInData",
				dataAttribute) instanceof List<DataAtomic>);
	}

	@Test
	public void testGetAllDataAtomicsWithNameInDataAndAttributes() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);
		DataAttribute dataAttribute = new DataAttributeSpy();

		List<DataAtomic> retunedValue = (List<DataAtomic>) dataGroup
				.getAllDataAtomicsWithNameInDataAndAttributes("nameInData", dataAttribute);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		Object[] returnValue = (Object[]) mcrForSpy.getParameterForMethodAndCallNumberAndParameter(
				ADD_CALL_AND_RETURN_FROM_MRV, 0, "childAttributes");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childAttributes", returnValue);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetFirstGroupWithNameInData() {
		assertTrue(dataGroup.getFirstGroupWithNameInData("nameInData") instanceof DataGroupSpy);
	}

	@Test
	public void testGetFirstGroupWithNameInData() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataGroupSpy::new);

		DataGroup retunedValue = dataGroup.getFirstGroupWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllGroupsWithNameInData() {
		assertTrue(dataGroup
				.getAllGroupsWithNameInData("nameInData") instanceof Collection<DataGroup>);
	}

	@Test
	public void testGetAllGroupsWithNameInData() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);

		List<DataGroup> retunedValue = dataGroup.getAllGroupsWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllGroupsWithNameInDataAndAttributes() {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataGroup.getAllGroupsWithNameInDataAndAttributes("nameInData",
				dataAttribute) instanceof Collection<DataGroup>);
	}

	@Test
	public void testGetAllGroupsWithNameInDataAndAttributes() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);
		DataAttribute dataAttribute = new DataAttributeSpy();

		Collection<DataGroup> retunedValue = dataGroup
				.getAllGroupsWithNameInDataAndAttributes("nameInData", dataAttribute);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		Object[] returnValue = (Object[]) mcrForSpy.getParameterForMethodAndCallNumberAndParameter(
				ADD_CALL_AND_RETURN_FROM_MRV, 0, "childAttributes");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childAttributes", returnValue);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultRemoveFirstChildWithNameInData() {
		assertTrue(dataGroup.removeFirstChildWithNameInData("nameInData"));
	}

	@Test
	public void testRemoveFirstChildWithNameInData() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataGroup.removeFirstChildWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultRemoveAllChildrenWithNameInData() {
		assertTrue(dataGroup.removeAllChildrenWithNameInData("nameInData"));
	}

	@Test
	public void testRemoveAllChildrenWithNameInData() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataGroup.removeAllChildrenWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultRemoveAllChildrenWithNameInDataAndAttributes() {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataGroup.removeAllChildrenWithNameInDataAndAttributes("nameInData",
				dataAttribute));
	}

	@Test
	public void testRemoveAllChildrenWithNameInDataAndAttributes() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);
		DataAttribute dataAttribute = new DataAttributeSpy();

		boolean retunedValue = dataGroup.removeAllChildrenWithNameInDataAndAttributes("nameInData",
				dataAttribute);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		Object[] returnValue = (Object[]) mcrForSpy.getParameterForMethodAndCallNumberAndParameter(
				ADD_CALL_AND_RETURN_FROM_MRV, 0, "childAttributes");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childAttributes", returnValue);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllChildrenMatchingFilter() {
		DataChildFilter childFilter = new DataChildFilterSpy();
		assertTrue(dataGroup.getAllChildrenMatchingFilter(childFilter) instanceof List<DataChild>);
	}

	@Test
	public void testGetAllChildrenMatchingFilter() {
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
	public void testDefaultRemoveAllChildrenMatchingFilter() {
		DataChildFilter childFilter = new DataChildFilterSpy();
		assertTrue(dataGroup.removeAllChildrenMatchingFilter(childFilter));
	}

	@Test
	public void testRemoveAllChildrenMatchingFilter() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);
		DataChildFilter childFilter = new DataChildFilterSpy();

		boolean retunedValue = dataGroup.removeAllChildrenMatchingFilter(childFilter);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childFilter", childFilter);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultContainsChildWithName() {
		Class<DataChild> type = DataChild.class;
		String name = "name";

		boolean contains = dataGroup.containsChildOfTypeAndName(type, name);

		assertFalse(contains);
	}

	@Test
	public void testContainsChildWithName() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, () -> true);
		Class<DataChild> type = DataChild.class;
		String name = "name";

		boolean returnedValue = dataGroup.containsChildOfTypeAndName(type, name);

		assertTrue(returnedValue);
		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "type", type);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "name", name);
	}

	@Test
	public void testDefaultGetFirstChildOfTypeWithNameAndAttributes() {
		Class<DataChild> type = DataChild.class;
		String name = "name";

		DataChild returnedValue = dataGroup.getFirstChildOfTypeAndName(type, name);

		assertTrue(returnedValue instanceof DataChild);
	}

	@Test
	public void testGetFirstChildOfTypeWithNameAndAttributes() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				DataRecordLinkSpy::new);

		Class<DataRecordLinkSpy> type = DataRecordLinkSpy.class;
		String name = "name";

		DataRecordLink returnedValue = dataGroup.getFirstChildOfTypeAndName(type, name);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "type", type);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "name", name);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetChildrenOfType() {
		Class<DataChild> type = DataChild.class;

		List<DataChild> returnedValue = dataGroup.getChildrenOfType(type);

		assertTrue(returnedValue instanceof List<DataChild>);
	}

	@Test
	public void testGetChildrenOfType() {
		dataGroup.MCR = MCRSpy;
		List<DataRecordLinkSpy> listOfLinks = List.of(new DataRecordLinkSpy());

		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, () -> listOfLinks);

		Class<DataRecordLinkSpy> type = DataRecordLinkSpy.class;

		List<DataRecordLinkSpy> returnedValue = dataGroup.getChildrenOfType(type);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "type", type);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetChildrenOfTypeWithNameAndAttributes() {
		Class<DataChild> type = DataChild.class;
		String name = "name";

		List<DataChild> returnedValue = dataGroup.getChildrenOfTypeAndName(type, name);

		assertTrue(returnedValue instanceof List<DataChild>);
	}

	@Test
	public void testGetChildrenOfTypeWithNameAndAttributes() {
		dataGroup.MCR = MCRSpy;
		List<DataRecordLinkSpy> listOfLinks = List.of(new DataRecordLinkSpy());

		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, () -> listOfLinks);

		Class<DataRecordLinkSpy> type = DataRecordLinkSpy.class;
		String name = "name";

		List<DataRecordLinkSpy> returnedValue = dataGroup.getChildrenOfTypeAndName(type, name);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "type", type);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "name", name);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultRemoveFirstChildWithTypeNameAndAttributes() {
		Class<DataChild> type = DataChild.class;
		String name = "name";

		boolean returnedValue = dataGroup.removeFirstChildWithTypeAndName(type, name);

		assertFalse(returnedValue);
	}

	@Test
	public void testRemoveFirstChildWithTypeNameAndAttributes() {
		dataGroup.MCR = MCRSpy;

		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, () -> true);

		Class<DataRecordLinkSpy> type = DataRecordLinkSpy.class;
		String name = "name";

		boolean returnedValue = dataGroup.removeFirstChildWithTypeAndName(type, name);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "type", type);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "name", name);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultRemoveChildrenWithTypeNameAndAttributes() {
		Class<DataChild> type = DataChild.class;
		String name = "name";

		boolean returnedValue = dataGroup.removeChildrenWithTypeAndName(type, name);

		assertFalse(returnedValue);
	}

	@Test
	public void testRemoveFirstChildrenWithTypeNameAndAttributes() {
		dataGroup.MCR = MCRSpy;

		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, () -> true);

		Class<DataRecordLinkSpy> type = DataRecordLinkSpy.class;
		String name = "name";

		boolean returnedValue = dataGroup.removeChildrenWithTypeAndName(type, name);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "type", type);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "name", name);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetAttributeValue() {
		Optional<String> returnedValue = dataGroup.getAttributeValue("someNameInData");

		assertTrue(returnedValue.isEmpty());
	}

	@Test
	public void testGetAttributeValue() {
		dataGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				() -> Optional.of("someValueToReturn"));

		Optional<String> returnedValue = dataGroup.getAttributeValue("someNameInData");

		assertTrue(returnedValue.isPresent());
		assertEquals(returnedValue.get(), "someValueToReturn");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "someNameInData");
	}
}
