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

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.data.Action;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;
import se.uu.ub.cora.testutils.spies.MCRSpy;

public class DataRecordSpyTest {
	private static final String ADD_CALL = "addCall";
	private static final String ADD_CALL_AND_RETURN_FROM_MRV = "addCallAndReturnFromMRV";
	DataRecordSpy dataRecord;
	private MCRSpy MCRSpy;
	private MethodCallRecorder mcrForSpy;

	@BeforeMethod
	public void beforeMethod() {
		MCRSpy = new MCRSpy();
		mcrForSpy = MCRSpy.MCR;
		dataRecord = new DataRecordSpy();
	}

	@Test
	public void testMakeSureSpyHelpersAreSetUp() throws Exception {
		assertTrue(dataRecord.MCR instanceof MethodCallRecorder);
		assertTrue(dataRecord.MRV instanceof MethodReturnValues);
		assertSame(dataRecord.MCR.onlyForTestGetMRV(), dataRecord.MRV);
	}

	@Test
	public void testDefaultGetType() throws Exception {
		assertTrue(dataRecord.getType() instanceof String);
	}

	@Test
	public void testGetType() throws Exception {
		dataRecord.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataRecord.getType();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetId() throws Exception {
		assertTrue(dataRecord.getId() instanceof String);
	}

	@Test
	public void testGetId() throws Exception {
		dataRecord.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataRecord.getId();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testSetDataGroup() throws Exception {
		dataRecord.MCR = MCRSpy;
		DataGroupSpy dataGroup = new DataGroupSpy();

		dataRecord.setDataGroup(dataGroup);

		mcrForSpy.assertParameter(ADD_CALL, 0, "dataGroup", dataGroup);
	}

	@Test
	public void testDefaultGetDataGroup() throws Exception {
		assertTrue(dataRecord.getDataGroup() instanceof DataGroupSpy);
	}

	@Test
	public void testGetDataGroup() throws Exception {
		dataRecord.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataGroupSpy::new);

		var returnedValue = dataRecord.getDataGroup();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testAddAction() throws Exception {
		dataRecord.MCR = MCRSpy;
		Action someAction = Action.BATCH_INDEX;

		dataRecord.addAction(someAction);

		mcrForSpy.assertParameter(ADD_CALL, 0, "action", someAction);
	}

	@Test
	public void testDefaultHasActions() throws Exception {
		assertFalse(dataRecord.hasActions());
	}

	@Test
	public void testHasActions() throws Exception {
		dataRecord.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> true);

		var returnedValue = dataRecord.hasActions();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetActions() throws Exception {
		assertTrue(dataRecord.getActions() instanceof List<Action>);
	}

	@Test
	public void testGetActions() throws Exception {
		dataRecord.MCR = MCRSpy;

		List<Action> actions = List.of(Action.CREATE, Action.DELETE);

		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<List<Action>>) () -> actions);

		var returnedValue = dataRecord.getActions();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testAddReadPermission() throws Exception {
		dataRecord.MCR = MCRSpy;

		dataRecord.addReadPermission("aReadPermission");

		mcrForSpy.assertParameter(ADD_CALL, 0, "readPermission", "aReadPermission");
	}

	@Test
	public void testAddReadPermissions() throws Exception {
		dataRecord.MCR = MCRSpy;

		Collection<String> readPermissions = Set.of("aReadPermission", "anotherReadPermission");
		dataRecord.addReadPermissions(readPermissions);

		mcrForSpy.assertParameter(ADD_CALL, 0, "readPermissions", readPermissions);
	}

	@Test
	public void testDefaultHasReadPermissions() throws Exception {

		assertFalse(dataRecord.hasReadPermissions());
	}

	@Test
	public void testHasReadPermissions() throws Exception {
		dataRecord.MCR = MCRSpy;

		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> true);

		boolean returnedValue = dataRecord.hasReadPermissions();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetReadPermissions() throws Exception {

		Set<String> returnedValue = dataRecord.getReadPermissions();

		assertTrue(returnedValue.isEmpty());
	}

	@Test
	public void testGetReadPermissions() throws Exception {
		dataRecord.MCR = MCRSpy;
		Set<String> readPermissions = Set.of("aReadPermission", "anotherReadPermission");
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Set<String>>) () -> readPermissions);

		Set<String> returnedValue = dataRecord.getReadPermissions();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testAddWritePermission() throws Exception {
		dataRecord.MCR = MCRSpy;

		dataRecord.addWritePermission("aWritePermission");

		mcrForSpy.assertParameter(ADD_CALL, 0, "writePermission", "aWritePermission");
	}

	@Test
	public void testAddWritePermissions() throws Exception {
		dataRecord.MCR = MCRSpy;

		Collection<String> writePermissions = Set.of("aWritePermission", "anotherReadPermission");
		dataRecord.addWritePermissions(writePermissions);

		mcrForSpy.assertParameter(ADD_CALL, 0, "writePermissions", writePermissions);
	}

	@Test
	public void testDefaultHasWritePermissions() throws Exception {
		assertFalse(dataRecord.hasWritePermissions());
	}

	@Test
	public void testHasWritePermissions() throws Exception {

		dataRecord.MCR = MCRSpy;

		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> true);

		boolean returnedValue = dataRecord.hasWritePermissions();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetWritePermissions() throws Exception {
		Set<String> returnedValue = dataRecord.getWritePermissions();

		assertTrue(returnedValue.isEmpty());
	}

	@Test
	public void testGetWritePermissions() throws Exception {
		dataRecord.MCR = MCRSpy;
		Set<String> writePermissions = Set.of("aWritePermission", "anotherWritePermission");
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Set<String>>) () -> writePermissions);

		Set<String> returnedValue = dataRecord.getWritePermissions();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetSearchId() throws Exception {
		assertTrue(dataRecord.getSearchId() instanceof String);
	}

	@Test
	public void testGetSearchId() throws Exception {
		dataRecord.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataRecord.getSearchId();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testAddProtocol() throws Exception {
		dataRecord.MCR = MCRSpy;

		dataRecord.addProtocol("someProtocol");

		mcrForSpy.assertParameter(ADD_CALL, 0, "protocol", "someProtocol");
	}

	@Test
	public void testDefaultGetProtocols() throws Exception {
		Set<String> returnedValue = dataRecord.getProtocols();

		assertTrue(returnedValue.isEmpty());
	}

	@Test
	public void testGetProtocols() throws Exception {
		dataRecord.MCR = MCRSpy;
		Set<String> protocols = Set.of("someProtocol", "someOtherProtocol");
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Set<String>>) () -> protocols);

		Set<String> returnedValue = dataRecord.getProtocols();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}
}
