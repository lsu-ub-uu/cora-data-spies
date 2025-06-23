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

import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.data.DataAtomic;
import se.uu.ub.cora.data.DataAttribute;
import se.uu.ub.cora.data.DataChildFilter;
import se.uu.ub.cora.data.DataGroup;
import se.uu.ub.cora.data.DataList;
import se.uu.ub.cora.data.DataRecord;
import se.uu.ub.cora.data.DataRecordGroup;
import se.uu.ub.cora.data.DataRecordLink;
import se.uu.ub.cora.data.DataResourceLink;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;
import se.uu.ub.cora.testutils.spies.MCRSpy;

public class DataFactorySpyTest {
	private static final String ADD_CALL_AND_RETURN_FROM_MRV = "addCallAndReturnFromMRV";
	DataFactorySpy dataFactory;
	private MCRSpy MCRSpy;
	private MethodCallRecorder mcrForSpy;

	@BeforeMethod
	public void beforeMethod() {
		MCRSpy = new MCRSpy();
		mcrForSpy = MCRSpy.MCR;
		dataFactory = new DataFactorySpy();
	}

	@Test
	public void testMakeSureSpyHelpersAreSetUp() {
		assertTrue(dataFactory.MCR instanceof MethodCallRecorder);
		assertTrue(dataFactory.MRV instanceof MethodReturnValues);
		assertSame(dataFactory.MCR.onlyForTestGetMRV(), dataFactory.MRV);
	}

	@Test
	public void testDefaultFactorListUsingNameOfDataType() {
		assertTrue(
				dataFactory.factorListUsingNameOfDataType("nameOfDataType") instanceof DataListSpy);
	}

	@Test
	public void testFactorListUsingNameOfDataType() {
		dataFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataListSpy::new);

		DataList retunedValue = dataFactory.factorListUsingNameOfDataType("nameOfDataType");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameOfDataType",
				"nameOfDataType");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultFactorRecordUsingDataRecordGroup() {
		DataRecordGroup dataRecordGroupSpy = new DataRecordGroupSpy();
		assertTrue(dataFactory
				.factorRecordUsingDataRecordGroup(dataRecordGroupSpy) instanceof DataRecordSpy);
	}

	@Test
	public void testFactorRecordUsingDataRecordGroup() {
		dataFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataRecordSpy::new);
		DataRecordGroup dataRecordGroupSpy = new DataRecordGroupSpy();

		DataRecord retunedValue = dataFactory.factorRecordUsingDataRecordGroup(dataRecordGroupSpy);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "dataRecordGroup",
				dataRecordGroupSpy);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultFactorRecordGroupUsingNameInData() {
		assertTrue(dataFactory
				.factorRecordGroupUsingNameInData("nameInData") instanceof DataRecordGroupSpy);
	}

	@Test
	public void testFactorRecordGroupUsingNameInData() {
		dataFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				DataRecordGroupSpy::new);

		DataRecordGroup retunedValue = dataFactory.factorRecordGroupUsingNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultFactorRecordGroupFromDataGroup() {
		DataGroup dataGroupSpy = new DataGroupSpy();
		assertTrue(dataFactory
				.factorRecordGroupFromDataGroup(dataGroupSpy) instanceof DataRecordGroupSpy);
	}

	@Test
	public void testFactorRecordGroupFromDataGroup() {
		dataFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				DataRecordGroupSpy::new);
		DataGroup dataGroupSpy = new DataGroupSpy();

		DataRecordGroup retunedValue = dataFactory.factorRecordGroupFromDataGroup(dataGroupSpy);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "dataGroup", dataGroupSpy);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultFactorGroupFromDataRecordGroup() {
		DataRecordGroup dataRecordGroupSpy = new DataRecordGroupSpy();
		assertTrue(dataFactory
				.factorGroupFromDataRecordGroup(dataRecordGroupSpy) instanceof DataGroupSpy);
	}

	@Test
	public void testFactorGroupFromDataRecordGroup() {
		dataFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataGroupSpy::new);
		DataRecordGroup dataRecordGroupSpy = new DataRecordGroupSpy();

		DataGroup retunedValue = dataFactory.factorGroupFromDataRecordGroup(dataRecordGroupSpy);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "dataRecordGroup",
				dataRecordGroupSpy);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultFactorGroupUsingNameInData() {
		assertTrue(dataFactory.factorGroupUsingNameInData("nameInData") instanceof DataGroupSpy);
	}

	@Test
	public void testFactorGroupUsingNameInData() {
		dataFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataGroupSpy::new);

		DataGroup retunedValue = dataFactory.factorGroupUsingNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultFactorRecordLinkUsingNameInData() {
		assertTrue(dataFactory
				.factorRecordLinkUsingNameInData("nameInData") instanceof DataRecordLinkSpy);
	}

	@Test
	public void testFactorRecordLinkUsingNameInData() {
		dataFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				DataRecordLinkSpy::new);

		DataRecordLink retunedValue = dataFactory.factorRecordLinkUsingNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultFactorRecordLinkUsingNameInDataAndTypeAndId() {
		assertTrue(dataFactory.factorRecordLinkUsingNameInDataAndTypeAndId("nameInData", "type",
				"id") instanceof DataRecordLinkSpy);
	}

	@Test
	public void testFactorRecordLinkUsingNameInDataAndTypeAndId() {
		dataFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				DataRecordLinkSpy::new);

		DataRecordLink retunedValue = dataFactory
				.factorRecordLinkUsingNameInDataAndTypeAndId("nameInData", "type", "id");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "recordType", "type");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "recordId", "id");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultFactorResourceLinkUsingNameInData() {
		assertTrue(dataFactory.factorResourceLinkUsingNameInDataAndTypeAndIdAndMimeType("nameInData",
				"someType", "someId", "someMimeType") instanceof DataResourceLinkSpy);
	}

	@Test
	public void testFactorResourceLinkUsingNameInData() {
		dataFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				DataResourceLinkSpy::new);

		DataResourceLink retunedValue = dataFactory.factorResourceLinkUsingNameInDataAndTypeAndIdAndMimeType(
				"nameInData", "someType", "someId", "someMimeType");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "recordType", "someType");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "recordId", "someId");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "mimeType", "someMimeType");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultFactorAtomicUsingNameInDataAndValue() {
		assertTrue(dataFactory.factorAtomicUsingNameInDataAndValue("nameInData",
				"value") instanceof DataAtomicSpy);
	}

	@Test
	public void testFactorAtomicUsingNameInDataAndValue() {
		dataFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataAtomicSpy::new);

		DataAtomic retunedValue = dataFactory.factorAtomicUsingNameInDataAndValue("nameInData",
				"value");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "value", "value");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultFactorAtomicUsingNameInDataAndValueAndRepeatId() {
		assertTrue(dataFactory.factorAtomicUsingNameInDataAndValueAndRepeatId("nameInData", "value",
				"repeatId") instanceof DataAtomicSpy);
	}

	@Test
	public void testFactorAtomicUsingNameInDataAndValueAndRepeatId() {
		dataFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataAtomicSpy::new);

		DataAtomic retunedValue = dataFactory
				.factorAtomicUsingNameInDataAndValueAndRepeatId("nameInData", "value", "repeatId");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "value", "value");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "repeatId", "repeatId");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultFactorAttributeUsingNameInDataAndValue() {
		assertTrue(dataFactory.factorAttributeUsingNameInDataAndValue("nameInData",
				"value") instanceof DataAttributeSpy);
	}

	@Test
	public void testFactorAttributeUsingNameInDataAndValue() {
		dataFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				DataAttributeSpy::new);

		DataAttribute retunedValue = dataFactory
				.factorAttributeUsingNameInDataAndValue("nameInData", "value");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "nameInData", "nameInData");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "value", "value");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultFactorDataChildFilterUsingNameInData() {
		assertTrue(dataFactory
				.factorDataChildFilterUsingNameInData("nameInData") instanceof DataChildFilterSpy);
	}

	@Test
	public void testFactorDataChildFilterUsingNameInData() {
		dataFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				DataChildFilterSpy::new);

		DataChildFilter retunedValue = dataFactory
				.factorDataChildFilterUsingNameInData("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "childNameInData", "nameInData");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}
}
