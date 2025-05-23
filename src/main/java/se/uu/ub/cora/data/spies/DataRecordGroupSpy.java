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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import se.uu.ub.cora.data.DataAtomic;
import se.uu.ub.cora.data.DataAttribute;
import se.uu.ub.cora.data.DataChild;
import se.uu.ub.cora.data.DataChildFilter;
import se.uu.ub.cora.data.DataGroup;
import se.uu.ub.cora.data.DataRecordGroup;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;

public class DataRecordGroupSpy implements DataRecordGroup {
	public MethodCallRecorder MCR = new MethodCallRecorder();
	public MethodReturnValues MRV = new MethodReturnValues();

	public DataRecordGroupSpy() {
		MCR.useMRV(MRV);
		MRV.setDefaultReturnValuesSupplier("getNameInData", String::new);
		MRV.setDefaultReturnValuesSupplier("hasAttributes", () -> false);
		MRV.setDefaultReturnValuesSupplier("getAttribute", DataAttributeSpy::new);
		MRV.setDefaultReturnValuesSupplier("getAttributes", ArrayList<DataAttribute>::new);
		MRV.setDefaultReturnValuesSupplier("hasChildren", () -> true);
		MRV.setDefaultReturnValuesSupplier("containsChildWithNameInData", () -> false);
		MRV.setDefaultReturnValuesSupplier("getChildren", ArrayList<DataChild>::new);
		MRV.setDefaultReturnValuesSupplier("getAllChildrenWithNameInData",
				ArrayList<DataChild>::new);
		MRV.setDefaultReturnValuesSupplier("getAllChildrenWithNameInDataAndAttributes",
				ArrayList<DataChild>::new);
		MRV.setDefaultReturnValuesSupplier("getFirstChildWithNameInData", DataChildSpy::new);
		MRV.setDefaultReturnValuesSupplier("getFirstAtomicValueWithNameInData", String::new);
		MRV.setDefaultReturnValuesSupplier("getFirstDataAtomicWithNameInData", DataAtomicSpy::new);
		MRV.setDefaultReturnValuesSupplier("getAllDataAtomicsWithNameInData",
				ArrayList<DataAtomic>::new);
		MRV.setDefaultReturnValuesSupplier("getAllDataAtomicsWithNameInDataAndAttributes",
				ArrayList<DataAtomic>::new);
		MRV.setDefaultReturnValuesSupplier("getFirstGroupWithNameInData", DataGroupSpy::new);
		MRV.setDefaultReturnValuesSupplier("getAllGroupsWithNameInData", ArrayList<DataGroup>::new);
		MRV.setDefaultReturnValuesSupplier("getAllGroupsWithNameInDataAndAttributes",
				ArrayList<DataGroup>::new);
		MRV.setDefaultReturnValuesSupplier("removeFirstChildWithNameInData", () -> true);
		MRV.setDefaultReturnValuesSupplier("removeAllChildrenWithNameInData", () -> true);
		MRV.setDefaultReturnValuesSupplier("removeAllChildrenWithNameInDataAndAttributes",
				() -> true);
		MRV.setDefaultReturnValuesSupplier("getAllChildrenMatchingFilter",
				ArrayList<DataChild>::new);
		MRV.setDefaultReturnValuesSupplier("removeAllChildrenMatchingFilter", () -> true);
		MRV.setDefaultReturnValuesSupplier("containsChildOfTypeAndName", () -> false);
		MRV.setDefaultReturnValuesSupplier("getFirstChildOfTypeAndName", DataChildSpy::new);
		MRV.setDefaultReturnValuesSupplier("getChildrenOfType", ArrayList<DataChildSpy>::new);
		MRV.setDefaultReturnValuesSupplier("getChildrenOfTypeAndName",
				ArrayList<DataChildSpy>::new);
		MRV.setDefaultReturnValuesSupplier("removeFirstChildWithTypeAndName", () -> false);
		MRV.setDefaultReturnValuesSupplier("removeChildrenWithTypeAndName", () -> false);
		MRV.setDefaultReturnValuesSupplier("getAttributeValue", Optional::empty);
		MRV.setDefaultReturnValuesSupplier("getType", String::new);
		MRV.setDefaultReturnValuesSupplier("getId", String::new);
		MRV.setDefaultReturnValuesSupplier("getDataDivider", String::new);
		MRV.setDefaultReturnValuesSupplier("getValidationType", String::new);
		MRV.setDefaultReturnValuesSupplier("getCreatedBy", String::new);
		MRV.setDefaultReturnValuesSupplier("getTsCreated", String::new);
		MRV.setDefaultReturnValuesSupplier("getLatestUpdatedBy", String::new);
		MRV.setDefaultReturnValuesSupplier("getLatestTsUpdated", String::new);
		MRV.setDefaultReturnValuesSupplier("getAllUpdated", Collections::emptyList);
		MRV.setDefaultReturnValuesSupplier("overwriteProtectionShouldBeEnforced", () -> false);
		MRV.setDefaultReturnValuesSupplier("getTsVisibility", Optional::empty);
		MRV.setDefaultReturnValuesSupplier("getVisibility", Optional::empty);
		MRV.setDefaultReturnValuesSupplier("getPermissionUnit", Optional::empty);
	}

	@Override
	public void addAttributeByIdWithValue(String nameInData, String value) {
		MCR.addCall("nameInData", nameInData, "value", value);
	}

	@Override
	public boolean hasAttributes() {
		return (boolean) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public DataAttribute getAttribute(String nameInData) {
		return (DataAttribute) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<DataAttribute> getAttributes() {
		return (Collection<DataAttribute>) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public String getNameInData() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public boolean hasChildren() {
		return (boolean) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public boolean containsChildWithNameInData(String nameInData) {
		return (boolean) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@Override
	public void addChild(DataChild dataChild) {
		MCR.addCall("dataChild", dataChild);
	}

	@Override
	public void addChildren(Collection<DataChild> dataChildren) {
		MCR.addCall("dataChildren", dataChildren);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataChild> getChildren() {
		return (List<DataChild>) MCR.addCallAndReturnFromMRV();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataChild> getAllChildrenWithNameInData(String nameInData) {
		return (List<DataChild>) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataChild> getAllChildrenWithNameInDataAndAttributes(String nameInData,
			DataAttribute... childAttributes) {
		return (List<DataChild>) MCR.addCallAndReturnFromMRV("nameInData", nameInData,
				"childAttributes", childAttributes);
	}

	@Override
	public DataChild getFirstChildWithNameInData(String nameInData) {
		return (DataChild) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@Override
	public String getFirstAtomicValueWithNameInData(String nameInData) {
		return (String) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@Override
	public DataAtomic getFirstDataAtomicWithNameInData(String nameInData) {
		return (DataAtomic) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataAtomic> getAllDataAtomicsWithNameInData(String nameInData) {
		return (List<DataAtomic>) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<DataAtomic> getAllDataAtomicsWithNameInDataAndAttributes(String nameInData,
			DataAttribute... childAttributes) {
		return (Collection<DataAtomic>) MCR.addCallAndReturnFromMRV("nameInData", nameInData,
				"childAttributes", childAttributes);
	}

	@Override
	public DataGroup getFirstGroupWithNameInData(String nameInData) {
		return (DataGroup) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataGroup> getAllGroupsWithNameInData(String nameInData) {
		return (List<DataGroup>) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<DataGroup> getAllGroupsWithNameInDataAndAttributes(String nameInData,
			DataAttribute... childAttributes) {
		return (Collection<DataGroup>) MCR.addCallAndReturnFromMRV("nameInData", nameInData,
				"childAttributes", childAttributes);
	}

	@Override
	public boolean removeFirstChildWithNameInData(String nameInData) {
		return (boolean) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@Override
	public boolean removeAllChildrenWithNameInData(String nameInData) {
		return (boolean) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@Override
	public boolean removeAllChildrenWithNameInDataAndAttributes(String nameInData,
			DataAttribute... childAttributes) {
		return (boolean) MCR.addCallAndReturnFromMRV("nameInData", nameInData, "childAttributes",
				childAttributes);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataChild> getAllChildrenMatchingFilter(DataChildFilter childFilter) {
		return (List<DataChild>) MCR.addCallAndReturnFromMRV("childFilter", childFilter);
	}

	@Override
	public boolean removeAllChildrenMatchingFilter(DataChildFilter childFilter) {
		return (boolean) MCR.addCallAndReturnFromMRV("childFilter", childFilter);
	}

	@Override
	public <T> boolean containsChildOfTypeAndName(Class<T> type, String name) {
		return (boolean) MCR.addCallAndReturnFromMRV("type", type, "name", name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataChild> T getFirstChildOfTypeAndName(Class<T> type, String name) {
		return (T) MCR.addCallAndReturnFromMRV("type", type, "name", name);
	}

	@Override
	public <T extends DataChild> List<T> getChildrenOfType(Class<T> type) {
		return (List<T>) MCR.addCallAndReturnFromMRV("type", type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataChild> List<T> getChildrenOfTypeAndName(Class<T> type, String name) {
		return (List<T>) MCR.addCallAndReturnFromMRV("type", type, "name", name);
	}

	@Override
	public <T extends DataChild> boolean removeFirstChildWithTypeAndName(Class<T> type,
			String name) {
		return (boolean) MCR.addCallAndReturnFromMRV("type", type, "name", name);
	}

	@Override
	public <T extends DataChild> boolean removeChildrenWithTypeAndName(Class<T> type, String name) {
		return (boolean) MCR.addCallAndReturnFromMRV("type", type, "name", name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<String> getAttributeValue(String nameInData) {
		return (Optional<String>) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@Override
	public String getType() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void setType(String type) {
		MCR.addCall("type", type);
	}

	@Override
	public String getId() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void setId(String id) {
		MCR.addCall("id", id);
	}

	@Override
	public String getDataDivider() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void setDataDivider(String dataDivider) {
		MCR.addCall("dataDivider", dataDivider);
	}

	@Override
	public String getValidationType() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void setValidationType(String validationType) {
		MCR.addCall("validationType", validationType);
	}

	@Override
	public String getCreatedBy() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void setCreatedBy(String userId) {
		MCR.addCall("userId", userId);
	}

	@Override
	public String getTsCreated() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void setTsCreated(String tsCreated) {
		MCR.addCall("tsCreated", tsCreated);
	}

	@Override
	public void setTsCreatedToNow() {
		MCR.addCall();
	}

	@Override
	public String getLatestUpdatedBy() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public String getLatestTsUpdated() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void addUpdatedUsingUserIdAndTs(String userId, String tsUpdated) {
		MCR.addCall("userId", userId, "tsUpdated", tsUpdated);
	}

	@Override
	public void addUpdatedUsingUserIdAndTsNow(String userId) {
		MCR.addCall("userId", userId);
	}

	@Override
	public boolean overwriteProtectionShouldBeEnforced() {
		return (boolean) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void removeOverwriteProtection() {
		MCR.addCall();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataChild> getAllUpdated() {
		return (List<DataChild>) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void setAllUpdated(Collection<DataChild> updated) {
		MCR.addCall("updated", updated);
	}

	@Override
	public void setVisibility(String visibility) {
		MCR.addCall("visibility", visibility);
	}

	@Override
	public void setTsVisibility(String tsVisibility) {
		MCR.addCall("tsVisibility", tsVisibility);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<String> getVisibility() {
		return (Optional<String>) MCR.addCallAndReturnFromMRV();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<String> getTsVisibility() {
		return (Optional<String>) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void setTsVisibilityNow() {
		MCR.addCall();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<String> getPermissionUnit() {
		return (Optional<String>) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void setPermissionUnit(String permissionUnit) {
		MCR.addCall("permissionUnit", permissionUnit);
	}
}
