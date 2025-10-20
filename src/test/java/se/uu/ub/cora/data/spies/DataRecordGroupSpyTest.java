/*
 * Copyright 2022, 2024 Olov McKie
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
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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

public class DataRecordGroupSpyTest {
	private static final String ADD_CALL = "addCall";
	private static final String ADD_CALL_AND_RETURN_FROM_MRV = "addCallAndReturnFromMRV";
	DataRecordGroupSpy dataRecordGroup;
	private MCRSpy MCRSpy;
	private MethodCallRecorder mcrForSpy;

	@BeforeMethod
	public void beforeMethod() {
		MCRSpy = new MCRSpy();
		mcrForSpy = MCRSpy.MCR;
		dataRecordGroup = new DataRecordGroupSpy();
	}

	@Test
	public void testMakeSureSpyHelpersAreSetUp() {
		assertTrue(dataRecordGroup.MCR instanceof MethodCallRecorder);
		assertTrue(dataRecordGroup.MRV instanceof MethodReturnValues);
		assertSame(dataRecordGroup.MCR.onlyForTestGetMRV(), dataRecordGroup.MRV);
	}

	@Test
	public void testAddChildNoSpy() {
		DataChildSpy dataChild = new DataChildSpy();
		dataRecordGroup.addChild(dataChild);

		dataRecordGroup.MCR.assertParameter("addChild", 0, "dataChild", dataChild);
	}

	@Test
	public void testAddAttributeByIdWithValue() {
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.addAttributeByIdWithValue("attribId", "attribValue");

		mcrForSpy.assertParameter(ADD_CALL, 0, "nameInData", "attribId");
		mcrForSpy.assertParameter(ADD_CALL, 0, "value", "attribValue");
	}

	@Test
	public void testDefaultGetAttribute() {
		assertTrue(dataRecordGroup.getAttribute("nameInData") instanceof DataAttribute);
	}

	@Test
	public void testGetAttribute() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				DataAttributeSpy::new);

		var returnedValue = dataRecordGroup.getAttribute("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetAttributes() {
		assertTrue(dataRecordGroup.getAttributes() instanceof Collection<DataAttribute>);
	}

	@Test
	public void testGetAttributes() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAttribute>::new);

		var returnedValue = dataRecordGroup.getAttributes();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetNameInData() {
		assertTrue(dataRecordGroup.getNameInData() instanceof String);
	}

	@Test
	public void testGetNameInData() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataRecordGroup.getNameInData();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultHasChildren() {
		assertTrue(dataRecordGroup.hasChildren());
	}

	@Test
	public void testHasChildren() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataRecordGroup.hasChildren();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultContainsChildWithNameInData() {
		assertFalse(dataRecordGroup.containsChildWithNameInData("nameInData"));
	}

	@Test
	public void testContainsChildWithNameInData() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataRecordGroup.containsChildWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testAddChild() {
		DataChildSpy dataChild = new DataChildSpy();
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.addChild(dataChild);

		mcrForSpy.assertParameter(ADD_CALL, 0, "dataChild", dataChild);
	}

	@Test
	public void testAddChildren() {
		Collection<DataChild> children = new ArrayList<>();
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.addChildren(children);

		mcrForSpy.assertParameter(ADD_CALL, 0, "dataChildren", children);
	}

	@Test
	public void testDefaultGetChildren() {
		assertTrue(dataRecordGroup.getChildren() instanceof List<DataChild>);
	}

	@Test
	public void testGetChildren() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChild>::new);

		List<DataChild> retunedValue = dataRecordGroup.getChildren();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllChildrenWithNameInData() {
		assertTrue(dataRecordGroup
				.getAllChildrenWithNameInData("nameInData") instanceof List<DataChild>);
	}

	@Test
	public void testGetAllChildrenWithNameInData() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChild>::new);

		List<DataChild> retunedValue = dataRecordGroup.getAllChildrenWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllChildrenWithNameInDataAndAttributes() {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataRecordGroup.getAllChildrenWithNameInDataAndAttributes("nameInData",
				dataAttribute) instanceof List<DataChild>);
	}

	@Test
	public void testGetAllChildrenWithNameInDataAndAttributes() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChild>::new);
		DataAttribute dataAttribute = new DataAttributeSpy();

		List<DataChild> retunedValue = dataRecordGroup
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
		assertTrue(
				dataRecordGroup.getFirstChildWithNameInData("nameInData") instanceof DataChildSpy);
	}

	@Test
	public void testGetFirstChildWithNameInData() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataChildSpy::new);

		DataChild retunedValue = dataRecordGroup.getFirstChildWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetFirstAtomicValueWithNameInData() {
		assertTrue(
				dataRecordGroup.getFirstAtomicValueWithNameInData("nameInData") instanceof String);
	}

	@Test
	public void testGetFirstAtomicValueWithNameInData() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String retunedValue = dataRecordGroup.getFirstAtomicValueWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetFirstDataAtomicWithNameInData() {
		assertTrue(dataRecordGroup
				.getFirstDataAtomicWithNameInData("nameInData") instanceof DataAtomicSpy);
	}

	@Test
	public void testGetFirstDataAtomicWithNameInData() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataAtomicSpy::new);

		DataChild retunedValue = dataRecordGroup.getFirstDataAtomicWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllDataAtomicsWithNameInData() {
		assertTrue(dataRecordGroup
				.getAllDataAtomicsWithNameInData("nameInData") instanceof List<DataAtomic>);
	}

	@Test
	public void testGetAllDataAtomicsWithNameInData() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);

		List<DataAtomic> retunedValue = dataRecordGroup
				.getAllDataAtomicsWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllDataAtomicsWithNameInDataAndAttributes() {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataRecordGroup.getAllDataAtomicsWithNameInDataAndAttributes("nameInData",
				dataAttribute) instanceof List<DataAtomic>);
	}

	@Test
	public void testGetAllDataAtomicsWithNameInDataAndAttributes() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);
		DataAttribute dataAttribute = new DataAttributeSpy();

		List<DataAtomic> retunedValue = (List<DataAtomic>) dataRecordGroup
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
		assertTrue(
				dataRecordGroup.getFirstGroupWithNameInData("nameInData") instanceof DataGroupSpy);
	}

	@Test
	public void testGetFirstGroupWithNameInData() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataGroupSpy::new);

		DataGroup retunedValue = dataRecordGroup.getFirstGroupWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllGroupsWithNameInData() {
		assertTrue(dataRecordGroup
				.getAllGroupsWithNameInData("nameInData") instanceof Collection<DataGroup>);
	}

	@Test
	public void testGetAllGroupsWithNameInData() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);

		List<DataGroup> retunedValue = dataRecordGroup.getAllGroupsWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllGroupsWithNameInDataAndAttributes() {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataRecordGroup.getAllGroupsWithNameInDataAndAttributes("nameInData",
				dataAttribute) instanceof Collection<DataGroup>);
	}

	@Test
	public void testGetAllGroupsWithNameInDataAndAttributes() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);
		DataAttribute dataAttribute = new DataAttributeSpy();

		Collection<DataGroup> retunedValue = dataRecordGroup
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
		assertTrue(dataRecordGroup.removeFirstChildWithNameInData("nameInData"));
	}

	@Test
	public void testRemoveFirstChildWithNameInData() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataRecordGroup.removeFirstChildWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultRemoveAllChildrenWithNameInData() {
		assertTrue(dataRecordGroup.removeAllChildrenWithNameInData("nameInData"));
	}

	@Test
	public void testRemoveAllChildrenWithNameInData() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataRecordGroup.removeAllChildrenWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultRemoveAllChildrenWithNameInDataAndAttributes() {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataRecordGroup.removeAllChildrenWithNameInDataAndAttributes("nameInData",
				dataAttribute));
	}

	@Test
	public void testRemoveAllChildrenWithNameInDataAndAttributes() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);
		DataAttribute dataAttribute = new DataAttributeSpy();

		boolean retunedValue = dataRecordGroup
				.removeAllChildrenWithNameInDataAndAttributes("nameInData", dataAttribute);

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
		assertTrue(dataRecordGroup
				.getAllChildrenMatchingFilter(childFilter) instanceof List<DataChild>);
	}

	@Test
	public void testGetAllChildrenMatchingFilter() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChildFilter>::new);
		DataChildFilter childFilter = new DataChildFilterSpy();

		List<DataChild> retunedValue = dataRecordGroup.getAllChildrenMatchingFilter(childFilter);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childFilter", childFilter);

		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultRemoveAllChildrenMatchingFilter() {
		DataChildFilter childFilter = new DataChildFilterSpy();
		assertTrue(dataRecordGroup.removeAllChildrenMatchingFilter(childFilter));
	}

	@Test
	public void testRemoveAllChildrenMatchingFilter() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);
		DataChildFilter childFilter = new DataChildFilterSpy();

		boolean retunedValue = dataRecordGroup.removeAllChildrenMatchingFilter(childFilter);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childFilter", childFilter);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultContainsChildWithName() {
		Class<DataChild> type = DataChild.class;
		String name = "someNameInData";
		assertFalse(dataRecordGroup.containsChildOfTypeAndName(type, name));
	}

	@Test
	public void testDefaultGetFirstChildOfTypeAndName() {
		Class<DataChild> type = DataChild.class;
		String name = "name";

		DataChild returnedValue = dataRecordGroup.getFirstChildOfTypeAndName(type, name);

		assertTrue(returnedValue instanceof DataChild);
	}

	@Test
	public void testGetFirstChildOfTypeAndName() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				DataRecordLinkSpy::new);

		Class<DataRecordLinkSpy> type = DataRecordLinkSpy.class;
		String name = "name";

		DataRecordLink returnedValue = dataRecordGroup.getFirstChildOfTypeAndName(type, name);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "type", type);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "name", name);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetChildrenOfTypeAndName() {
		Class<DataChild> type = DataChild.class;
		String name = "name";

		List<DataChild> returnedValue = dataRecordGroup.getChildrenOfTypeAndName(type, name);

		assertTrue(returnedValue instanceof List<DataChild>);
	}

	@Test
	public void testDefaultGetChildrenOfType() {
		Class<DataChild> type = DataChild.class;

		List<DataChild> returnedValue = dataRecordGroup.getChildrenOfType(type);

		assertTrue(returnedValue instanceof List<DataChild>);
	}

	@Test
	public void testGetChildrenOfType() {
		dataRecordGroup.MCR = MCRSpy;
		List<DataRecordLinkSpy> listOfLinks = List.of(new DataRecordLinkSpy());

		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, () -> listOfLinks);

		Class<DataRecordLinkSpy> type = DataRecordLinkSpy.class;

		List<DataRecordLinkSpy> returnedValue = dataRecordGroup.getChildrenOfType(type);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "type", type);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testGetChildrenOfTypeAndName() {
		dataRecordGroup.MCR = MCRSpy;
		List<DataRecordLinkSpy> listOfLinks = List.of(new DataRecordLinkSpy());

		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, () -> listOfLinks);

		Class<DataRecordLinkSpy> type = DataRecordLinkSpy.class;
		String name = "name";

		List<DataRecordLinkSpy> returnedValue = dataRecordGroup.getChildrenOfTypeAndName(type,
				name);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "type", type);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "name", name);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultRemoveFirstChildWithTypeAndName() {
		Class<DataChild> type = DataChild.class;
		String name = "name";

		boolean returnedValue = dataRecordGroup.removeFirstChildWithTypeAndName(type, name);

		assertFalse(returnedValue);
	}

	@Test
	public void testRemoveFirstChildWithTypeAndName() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, () -> true);

		Class<DataRecordLinkSpy> type = DataRecordLinkSpy.class;
		String name = "name";

		boolean returnedValue = dataRecordGroup.removeFirstChildWithTypeAndName(type, name);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "type", type);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "name", name);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultRemoveChildrenWithTypeAndName() {
		Class<DataChild> type = DataChild.class;
		String name = "name";

		boolean returnedValue = dataRecordGroup.removeChildrenWithTypeAndName(type, name);

		assertFalse(returnedValue);
	}

	@Test
	public void testRemoveFirstChildrenWithTypeAndName() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, () -> true);

		Class<DataRecordLinkSpy> type = DataRecordLinkSpy.class;
		String name = "name";

		boolean returnedValue = dataRecordGroup.removeChildrenWithTypeAndName(type, name);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "type", type);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "name", name);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetAttributeValue() {
		Optional<String> returnedValue = dataRecordGroup.getAttributeValue("someNameInData");

		assertTrue(returnedValue.isEmpty());
	}

	@Test
	public void testGetAttributeValue() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				() -> Optional.of("someValueToReturn"));

		Optional<String> returnedValue = dataRecordGroup.getAttributeValue("someNameInData");

		assertTrue(returnedValue.isPresent());
		assertEquals(returnedValue.get(), "someValueToReturn");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "someNameInData");
	}

	@DataProvider(name = "getString")
	public Object[][] testCasesForGetString() {
		GetString type = new GetString(() -> dataRecordGroup.getType(), "");
		GetString id = new GetString(() -> dataRecordGroup.getId(), "");
		GetString dataDivider = new GetString(() -> dataRecordGroup.getDataDivider(), "");
		GetString validationType = new GetString(() -> dataRecordGroup.getValidationType(), "");
		GetString createdBy = new GetString(() -> dataRecordGroup.getCreatedBy(), "");
		GetString tsCreated = new GetString(() -> dataRecordGroup.getTsCreated(), "");
		GetString latestUpdatedBy = new GetString(() -> dataRecordGroup.getLatestUpdatedBy(), "");
		GetString latesTsUpdated = new GetString(() -> dataRecordGroup.getLatestTsUpdated(), "");

		return new GetString[][] { { type }, { id }, { dataDivider }, { validationType },
				{ createdBy }, { tsCreated }, { latestUpdatedBy }, { latesTsUpdated } };
	}

	public record GetString(Supplier<Object> methodToRun, Object defaultValue) {
	}

	@Test(dataProvider = "getString")
	public void testGetStringDefaultValue(GetString testData) {
		assertTrue(testData.methodToRun.get() instanceof String);
		assertEquals(testData.methodToRun.get(), testData.defaultValue);
	}

	@Test(dataProvider = "getString")
	public void testGetString(GetString testData) {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, () -> "someValue");

		var returnedValue = testData.methodToRun.get();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@DataProvider(name = "setString")
	public Object[][] testCasesForSetString() {
		SetString type = new SetString(value -> dataRecordGroup.setType(value), "type");
		SetString id = new SetString(value -> dataRecordGroup.setId(value), "id");
		SetString dataDivider = new SetString(value -> dataRecordGroup.setDataDivider(value),
				"dataDivider");
		SetString validationType = new SetString(value -> dataRecordGroup.setValidationType(value),
				"validationType");
		SetString createdBy = new SetString(value -> dataRecordGroup.setCreatedBy(value), "userId");
		SetString tsCreated = new SetString(value -> dataRecordGroup.setTsCreated(value),
				"tsCreated");
		SetString addUpdatedUserIdNow = new SetString(
				value -> dataRecordGroup.addUpdatedUsingUserIdAndTsNow(value), "userId");

		return new SetString[][] { { type }, { id }, { dataDivider }, { validationType },
				{ createdBy }, { tsCreated }, { addUpdatedUserIdNow } };
	}

	public record SetString(Consumer<String> methodToRun, String parameterName) {
	}

	@Test(dataProvider = "setString")
	public void testSetString(SetString testData) {
		dataRecordGroup.MCR = MCRSpy;

		testData.methodToRun.accept("someValue");

		mcrForSpy.assertParameter(ADD_CALL, 0, testData.parameterName, "someValue");
	}

	@DataProvider(name = "getOptional")
	public Object[][] testCasesForGetOptional() {
		GetOptional visibility = new GetOptional(() -> dataRecordGroup.getVisibility(),
				Optional.empty());
		GetOptional tsVisibility = new GetOptional(() -> dataRecordGroup.getTsVisibility(),
				Optional.empty());
		GetOptional isInTrashBin = new GetOptional(() -> dataRecordGroup.isInTrashBin(),
				Optional.empty());
		GetOptional permissionUnit = new GetOptional(() -> dataRecordGroup.getPermissionUnit(),
				Optional.empty());

		return new GetOptional[][] { { visibility }, { tsVisibility }, { isInTrashBin },
				{ permissionUnit } };
	}

	public record GetOptional(Supplier<Optional<?>> methodToRun, Optional<String> defaultValue) {
	}

	@Test(dataProvider = "getOptional")
	public void testGetOptionalDefaultValue(GetOptional testData) {
		assertTrue(testData.methodToRun.get() instanceof Optional);
		assertEquals(testData.methodToRun.get(), testData.defaultValue);
	}

	@Test(dataProvider = "getOptional")
	public void testGetOptional(GetOptional testData) {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				() -> Optional.of("someValue"));

		var returnedValue = testData.methodToRun.get();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@DataProvider(name = "setOptional")
	public Object[][] testCasesForSetOptional() {
		SetOptional visibility = new SetOptional(value -> dataRecordGroup.setVisibility(value),
				"visibility");
		SetOptional tsVisibility = new SetOptional(value -> dataRecordGroup.setTsVisibility(value),
				"tsVisibility");
		SetOptional permissionUnit = new SetOptional(
				value -> dataRecordGroup.setPermissionUnit(value), "permissionUnit");

		return new SetOptional[][] { { visibility }, { tsVisibility }, { permissionUnit } };
	}

	public record SetOptional(Consumer<String> methodToRun, String parameterName) {
	}

	@Test(dataProvider = "setOptional")
	public void testSetOptional(SetOptional testData) {
		dataRecordGroup.MCR = MCRSpy;

		testData.methodToRun.accept("someValue");

		mcrForSpy.assertParameter(ADD_CALL, 0, testData.parameterName, "someValue");
	}

	@Test
	public void testSetTsCreatedToNow() {
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.setTsCreatedToNow();

		mcrForSpy.assertParameters(ADD_CALL, 0);
	}

	@Test
	public void testAddUpdatedUsingTsAndUserId() {
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.addUpdatedUsingUserIdAndTs("valueUser", "valueUpdated");

		mcrForSpy.assertParameter(ADD_CALL, 0, "userId", "valueUser");
		mcrForSpy.assertParameter(ADD_CALL, 0, "tsUpdated", "valueUpdated");
	}

	@DataProvider(name = "getBoolean")
	public Object[][] testCasesForGetBoolean() {
		GetBoolean hasAttributes = new GetBoolean(() -> dataRecordGroup.hasAttributes());
		GetBoolean overwriteProtectionShouldBeEnforced = new GetBoolean(
				() -> dataRecordGroup.overwriteProtectionShouldBeEnforced());

		return new GetBoolean[][] { { hasAttributes }, { overwriteProtectionShouldBeEnforced } };
	}

	public record GetBoolean(Supplier<Boolean> methodToRun) {
	}

	@Test(dataProvider = "getBoolean")
	public void testDefaultCaseGetBoolean(GetBoolean testData) {
		assertFalse(testData.methodToRun.get());
	}

	@Test(dataProvider = "getBoolean")
	public void testCaseGetBoolean(GetBoolean testData) {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> true);

		boolean retunedValue = testData.methodToRun.get();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void removeOverwriteProtection() {
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.removeOverwriteProtection();

		mcrForSpy.assertParameters(ADD_CALL, 0);
	}

	@Test
	public void testSetAllUpdated() {
		Collection<DataChild> updated = new ArrayList<>();
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.setAllUpdated(updated);

		mcrForSpy.assertParameter(ADD_CALL, 0, "updated", updated);
	}

	@Test
	public void testDefaultGetAllUpdated() {
		assertTrue(dataRecordGroup.getAllUpdated() instanceof List<DataChild>);
	}

	@Test
	public void testGetAllUpdated() {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChild>::new);

		List<DataChild> retunedValue = dataRecordGroup.getAllUpdated();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testSetVisibilityNow() {
		dataRecordGroup.MCR = MCRSpy;
		dataRecordGroup.setTsVisibilityNow();
		mcrForSpy.assertMethodWasCalled(ADD_CALL);
	}

	@Test()
	public void testSetInTrashBin() {
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.setInTrashBin(true);

		mcrForSpy.assertParameter(ADD_CALL, 0, "inTrashBin", true);
	}
}
