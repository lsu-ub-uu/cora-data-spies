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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import se.uu.ub.cora.data.Action;
import se.uu.ub.cora.data.DataGroup;
import se.uu.ub.cora.data.DataRecord;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;

@SuppressWarnings("exports")
public class DataRecordSpy implements DataRecord {
	public MethodCallRecorder MCR = new MethodCallRecorder();
	public MethodReturnValues MRV = new MethodReturnValues();

	public DataRecordSpy() {
		MCR.useMRV(MRV);
		MRV.setDefaultReturnValuesSupplier("getType", String::new);
		MRV.setDefaultReturnValuesSupplier("getId", String::new);
		MRV.setDefaultReturnValuesSupplier("getDataGroup", DataGroupSpy::new);
		MRV.setDefaultReturnValuesSupplier("hasActions", (Supplier<Boolean>) () -> false);
		MRV.setDefaultReturnValuesSupplier("getActions", Collections::emptyList);
		MRV.setDefaultReturnValuesSupplier("hasReadPermissions", (Supplier<Boolean>) () -> false);
		MRV.setDefaultReturnValuesSupplier("getReadPermissions", Collections::emptySet);
		MRV.setDefaultReturnValuesSupplier("hasWritePermissions", (Supplier<Boolean>) () -> false);
		MRV.setDefaultReturnValuesSupplier("getWritePermissions", Collections::emptySet);
		MRV.setDefaultReturnValuesSupplier("getSearchId", String::new);
	}

	@Override
	public String getType() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public String getId() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void setDataGroup(DataGroup dataGroup) {
		MCR.addCall("dataGroup", dataGroup);

	}

	@Override
	public DataGroup getDataGroup() {
		return (DataGroup) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void addAction(Action action) {
		MCR.addCall("action", action);
	}

	@Override
	public boolean hasActions() {
		return (boolean) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public List<Action> getActions() {
		return (List<Action>) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void addReadPermission(String readPermission) {
		MCR.addCall("readPermission", readPermission);
	}

	@Override
	public void addReadPermissions(Collection<String> readPermissions) {
		MCR.addCall("readPermissions", readPermissions);
	}

	@Override
	public Set<String> getReadPermissions() {
		return (Set<String>) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public boolean hasReadPermissions() {
		return (boolean) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void addWritePermission(String writePermission) {
		MCR.addCall("writePermission", writePermission);

	}

	@Override
	public void addWritePermissions(Collection<String> writePermissions) {
		MCR.addCall("writePermissions", writePermissions);

	}

	@Override
	public Set<String> getWritePermissions() {
		return (Set<String>) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public boolean hasWritePermissions() {
		return (boolean) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public String getSearchId() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

}
