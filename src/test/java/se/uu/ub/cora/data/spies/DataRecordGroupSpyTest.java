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
	public void testMakeSureSpyHelpersAreSetUp() throws Exception {
		assertTrue(dataRecordGroup.MCR instanceof MethodCallRecorder);
		assertTrue(dataRecordGroup.MRV instanceof MethodReturnValues);
		assertSame(dataRecordGroup.MCR.onlyForTestGetMRV(), dataRecordGroup.MRV);
	}

	@Test
	public void testAddChildNoSpy() throws Exception {
		DataChildSpy dataChild = new DataChildSpy();
		dataRecordGroup.addChild(dataChild);

		dataRecordGroup.MCR.assertParameter("addChild", 0, "dataChild", dataChild);
	}

	@Test
	public void testAddAttributeByIdWithValue() throws Exception {
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.addAttributeByIdWithValue("attribId", "attribValue");

		mcrForSpy.assertParameter(ADD_CALL, 0, "nameInData", "attribId");
		mcrForSpy.assertParameter(ADD_CALL, 0, "value", "attribValue");
	}

	@Test
	public void testDefaultHasAttributes() throws Exception {
		assertFalse(dataRecordGroup.hasAttributes());
	}

	@Test
	public void testHasAttributes() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> true);

		boolean retunedValue = dataRecordGroup.hasAttributes();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAttribute() throws Exception {
		assertTrue(dataRecordGroup.getAttribute("nameInData") instanceof DataAttribute);
	}

	@Test
	public void testGetAttribute() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				DataAttributeSpy::new);

		var returnedValue = dataRecordGroup.getAttribute("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetAttributes() throws Exception {
		assertTrue(dataRecordGroup.getAttributes() instanceof Collection<DataAttribute>);
	}

	@Test
	public void testGetAttributes() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAttribute>::new);

		var returnedValue = dataRecordGroup.getAttributes();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetNameInData() throws Exception {
		assertTrue(dataRecordGroup.getNameInData() instanceof String);
	}

	@Test
	public void testGetNameInData() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataRecordGroup.getNameInData();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultHasChildren() throws Exception {
		assertTrue(dataRecordGroup.hasChildren());
	}

	@Test
	public void testHasChildren() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataRecordGroup.hasChildren();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultContainsChildWithNameInData() throws Exception {
		assertFalse(dataRecordGroup.containsChildWithNameInData("nameInData"));
	}

	@Test
	public void testContainsChildWithNameInData() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataRecordGroup.containsChildWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testAddChild() throws Exception {
		DataChildSpy dataChild = new DataChildSpy();
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.addChild(dataChild);

		mcrForSpy.assertParameter(ADD_CALL, 0, "dataChild", dataChild);
	}

	@Test
	public void testAddChildren() throws Exception {
		Collection<DataChild> children = new ArrayList<>();
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.addChildren(children);

		mcrForSpy.assertParameter(ADD_CALL, 0, "dataChildren", children);
	}

	@Test
	public void testDefaultGetChildren() throws Exception {
		assertTrue(dataRecordGroup.getChildren() instanceof List<DataChild>);
	}

	@Test
	public void testGetChildren() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChild>::new);

		List<DataChild> retunedValue = dataRecordGroup.getChildren();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllChildrenWithNameInData() throws Exception {
		assertTrue(dataRecordGroup
				.getAllChildrenWithNameInData("nameInData") instanceof List<DataChild>);
	}

	@Test
	public void testGetAllChildrenWithNameInData() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChild>::new);

		List<DataChild> retunedValue = dataRecordGroup.getAllChildrenWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllChildrenWithNameInDataAndAttributes() throws Exception {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataRecordGroup.getAllChildrenWithNameInDataAndAttributes("nameInData",
				dataAttribute) instanceof List<DataChild>);
	}

	@Test
	public void testGetAllChildrenWithNameInDataAndAttributes() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataChild>::new);
		DataAttribute dataAttribute = new DataAttributeSpy();

		List<DataChild> retunedValue = dataRecordGroup
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
		assertTrue(
				dataRecordGroup.getFirstChildWithNameInData("nameInData") instanceof DataChildSpy);
	}

	@Test
	public void testGetFirstChildWithNameInData() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataChildSpy::new);

		DataChild retunedValue = dataRecordGroup.getFirstChildWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetFirstAtomicValueWithNameInData() throws Exception {
		assertTrue(
				dataRecordGroup.getFirstAtomicValueWithNameInData("nameInData") instanceof String);
	}

	@Test
	public void testGetFirstAtomicValueWithNameInData() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String retunedValue = dataRecordGroup.getFirstAtomicValueWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	// TODO
	@Test
	public void testDefaultGetFirstDataAtomicWithNameInData() throws Exception {
		assertTrue(dataRecordGroup
				.getFirstDataAtomicWithNameInData("nameInData") instanceof DataAtomicSpy);
	}

	@Test
	public void testGetFirstDataAtomicWithNameInData() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataAtomicSpy::new);

		DataChild retunedValue = dataRecordGroup.getFirstDataAtomicWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllDataAtomicsWithNameInData() throws Exception {
		assertTrue(dataRecordGroup
				.getAllDataAtomicsWithNameInData("nameInData") instanceof List<DataAtomic>);
	}

	@Test
	public void testGetAllDataAtomicsWithNameInData() throws Exception {
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
	public void testDefaultGetAllDataAtomicsWithNameInDataAndAttributes() throws Exception {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataRecordGroup.getAllDataAtomicsWithNameInDataAndAttributes("nameInData",
				dataAttribute) instanceof List<DataAtomic>);
	}

	@Test
	public void testGetAllDataAtomicsWithNameInDataAndAttributes() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);
		DataAttribute dataAttribute = new DataAttributeSpy();

		List<DataAtomic> retunedValue = (List<DataAtomic>) dataRecordGroup
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
		assertTrue(
				dataRecordGroup.getFirstGroupWithNameInData("nameInData") instanceof DataGroupSpy);
	}

	@Test
	public void testGetFirstGroupWithNameInData() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataGroupSpy::new);

		DataGroup retunedValue = dataRecordGroup.getFirstGroupWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllGroupsWithNameInData() throws Exception {
		assertTrue(dataRecordGroup
				.getAllGroupsWithNameInData("nameInData") instanceof Collection<DataGroup>);
	}

	@Test
	public void testGetAllGroupsWithNameInData() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);

		List<DataGroup> retunedValue = dataRecordGroup.getAllGroupsWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAllGroupsWithNameInDataAndAttributes() throws Exception {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataRecordGroup.getAllGroupsWithNameInDataAndAttributes("nameInData",
				dataAttribute) instanceof Collection<DataGroup>);
	}

	@Test
	public void testGetAllGroupsWithNameInDataAndAttributes() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAtomic>::new);
		DataAttribute dataAttribute = new DataAttributeSpy();

		Collection<DataGroup> retunedValue = dataRecordGroup
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
		assertTrue(dataRecordGroup.removeFirstChildWithNameInData("nameInData"));
	}

	@Test
	public void testRemoveFirstChildWithNameInData() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataRecordGroup.removeFirstChildWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultRemoveAllChildrenWithNameInData() throws Exception {
		assertTrue(dataRecordGroup.removeAllChildrenWithNameInData("nameInData"));
	}

	@Test
	public void testRemoveAllChildrenWithNameInData() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataRecordGroup.removeAllChildrenWithNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultRemoveAllChildrenWithNameInDataAndAttributes() throws Exception {
		DataAttribute dataAttribute = new DataAttributeSpy();
		assertTrue(dataRecordGroup.removeAllChildrenWithNameInDataAndAttributes("nameInData",
				dataAttribute));
	}

	@Test
	public void testRemoveAllChildrenWithNameInDataAndAttributes() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);
		DataAttribute dataAttribute = new DataAttributeSpy();

		boolean retunedValue = dataRecordGroup
				.removeAllChildrenWithNameInDataAndAttributes("nameInData", dataAttribute);

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
		assertTrue(dataRecordGroup
				.getAllChildrenMatchingFilter(childFilter) instanceof List<DataChild>);
	}

	@Test
	public void testGetAllChildrenMatchingFilter() throws Exception {
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
	public void testDefaultRemoveAllChildrenMatchingFilter() throws Exception {
		DataChildFilter childFilter = new DataChildFilterSpy();
		assertTrue(dataRecordGroup.removeAllChildrenMatchingFilter(childFilter));
	}

	@Test
	public void testRemoveAllChildrenMatchingFilter() throws Exception {
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
	public void testDefaultGetType() throws Exception {
		assertTrue(dataRecordGroup.getType() instanceof String);
	}

	@Test
	public void testGetType() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataRecordGroup.getType();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testSetType() throws Exception {
		String type = "someType";
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.setType(type);

		mcrForSpy.assertParameter(ADD_CALL, 0, "type", type);
	}

	@Test
	public void testDefaultGetId() throws Exception {
		assertTrue(dataRecordGroup.getId() instanceof String);
	}

	@Test
	public void testGetId() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataRecordGroup.getId();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testSetId() throws Exception {
		String id = "someId";
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.setId(id);

		mcrForSpy.assertParameter(ADD_CALL, 0, "id", id);
	}

	@Test
	public void testDefaultGetDataDivider() throws Exception {
		assertTrue(dataRecordGroup.getDataDivider() instanceof String);
	}

	@Test
	public void testGetDataDivider() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataRecordGroup.getDataDivider();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testSetDataDivider() throws Exception {
		String dataDivider = "someDataDivider";
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.setDataDivider(dataDivider);

		mcrForSpy.assertParameter(ADD_CALL, 0, "dataDivider", dataDivider);
	}

	@Test
	public void testDefaultGetValidationType() throws Exception {
		assertTrue(dataRecordGroup.getValidationType() instanceof String);
	}

	@Test
	public void testGetValidationType() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataRecordGroup.getValidationType();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testSetValidationType() throws Exception {
		String validationType = "someValidationType";
		dataRecordGroup.MCR = MCRSpy;

		dataRecordGroup.setValidationType(validationType);

		mcrForSpy.assertParameter(ADD_CALL, 0, "validationType", validationType);
	}

	@Test
	public void testDefaultContainsChildWithName() throws Exception {
		Class<DataChild> type = DataChild.class;
		String name = "someNameInData";
		assertFalse(dataRecordGroup.containsChildOfTypeAndName(type, name));
	}

	@Test
	public void testDefaultGetFirstChildOfTypeAndName() throws Exception {
		Class<DataChild> type = DataChild.class;
		String name = "name";

		DataChild returnedValue = dataRecordGroup.getFirstChildOfTypeAndName(type, name);

		assertTrue(returnedValue instanceof DataChild);
	}

	@Test
	public void testGetFirstChildOfTypeAndName() throws Exception {
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

	// @Test
	// public void testName() throws Exception {
	// DataRecordGroupSpy dataRecordGroup = new DataRecordGroupSpy();
	// DataRecordLinkSpy textLink = new DataRecordLinkSpy();
	//
	// dataRecordGroup.MRV.setSpecificReturnValuesSupplier("getFirstChildOfTypeAndName",
	// () -> textLink, DataRecordLink.class, "textId", new DataAttribute[0]);
	//
	// DataRecordLink returnedValue = dataRecordGroup
	// .getFirstChildOfTypeAndName(DataRecordLink.class, "textId");
	//
	// assertSame(returnedValue, textLink);
	// }
	//
	// @Test
	// public void testName2() throws Exception {
	// DataRecordGroupSpy dataRecordGroup = new DataRecordGroupSpy();
	//
	// DataRecordLinkSpy textLink = new DataRecordLinkSpy();
	//
	// DataAttributeSpy dataAttributeSpy = new DataAttributeSpy();
	// dataAttributeSpy.MRV.setDefaultReturnValuesSupplier("getNameInData",
	// () -> "someNameInData");
	// dataAttributeSpy.MRV.setDefaultReturnValuesSupplier("getValue", () -> "someValue");
	// dataRecordGroup.MRV.setSpecificReturnValuesSupplier("getFirstChildOfTypeAndName",
	// () -> textLink, DataRecordLink.class, "textId", dataAttributeSpy);
	//
	// DataRecordLink returnedValue = dataRecordGroup
	// .getFirstChildOfTypeAndName(DataRecordLink.class, "textId");
	//
	// assertSame(returnedValue, textLink);
	// }

	@Test
	public void testDefaultGetChildrenOfTypeAndName() throws Exception {
		Class<DataChild> type = DataChild.class;
		String name = "name";

		List<DataChild> returnedValue = dataRecordGroup.getChildrenOfTypeAndName(type, name);

		assertTrue(returnedValue instanceof List<DataChild>);
	}

	@Test
	public void testGetChildrenOfTypeAndName() throws Exception {
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
	public void testDefaultRemoveFirstChildWithTypeAndName() throws Exception {
		Class<DataChild> type = DataChild.class;
		String name = "name";

		boolean returnedValue = dataRecordGroup.removeFirstChildWithTypeAndName(type, name);

		assertFalse(returnedValue);
	}

	@Test
	public void testRemoveFirstChildWithTypeAndName() throws Exception {
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
	public void testDefaultRemoveChildrenWithTypeAndName() throws Exception {
		Class<DataChild> type = DataChild.class;
		String name = "name";

		boolean returnedValue = dataRecordGroup.removeChildrenWithTypeAndName(type, name);

		assertFalse(returnedValue);
	}

	@Test
	public void testRemoveFirstChildrenWithTypeAndName() throws Exception {
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
	public void testDefaultGetAttributeValue() throws Exception {

		Optional<String> returnedValue = dataRecordGroup.getAttributeValue("someNameInData");

		assertTrue(returnedValue.isEmpty());
	}

	@Test
	public void testGetAttributeValue() throws Exception {
		dataRecordGroup.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				() -> Optional.of("someValueToReturn"));

		Optional<String> returnedValue = dataRecordGroup.getAttributeValue("someNameInData");

		assertTrue(returnedValue.isPresent());
		assertEquals(returnedValue.get(), "someValueToReturn");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "someNameInData");
	}
}
