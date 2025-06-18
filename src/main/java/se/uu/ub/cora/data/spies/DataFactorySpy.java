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

import se.uu.ub.cora.data.DataAtomic;
import se.uu.ub.cora.data.DataAttribute;
import se.uu.ub.cora.data.DataChildFilter;
import se.uu.ub.cora.data.DataFactory;
import se.uu.ub.cora.data.DataGroup;
import se.uu.ub.cora.data.DataList;
import se.uu.ub.cora.data.DataRecord;
import se.uu.ub.cora.data.DataRecordGroup;
import se.uu.ub.cora.data.DataRecordLink;
import se.uu.ub.cora.data.DataResourceLink;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;

@SuppressWarnings("exports")
public class DataFactorySpy implements DataFactory {

	public MethodCallRecorder MCR = new MethodCallRecorder();
	public MethodReturnValues MRV = new MethodReturnValues();

	public DataFactorySpy() {
		MCR.useMRV(MRV);
		MRV.setDefaultReturnValuesSupplier("factorListUsingNameOfDataType", DataListSpy::new);
		MRV.setDefaultReturnValuesSupplier("factorRecordUsingDataRecordGroup", DataRecordSpy::new);
		MRV.setDefaultReturnValuesSupplier("factorRecordGroupUsingNameInData",
				DataRecordGroupSpy::new);
		MRV.setDefaultReturnValuesSupplier("factorRecordGroupFromDataGroup",
				DataRecordGroupSpy::new);
		MRV.setDefaultReturnValuesSupplier("factorGroupFromDataRecordGroup", DataGroupSpy::new);
		MRV.setDefaultReturnValuesSupplier("factorGroupUsingNameInData", DataGroupSpy::new);
		MRV.setDefaultReturnValuesSupplier("factorRecordLinkUsingNameInData",
				DataRecordLinkSpy::new);
		MRV.setDefaultReturnValuesSupplier("factorRecordLinkUsingNameInDataAndTypeAndId",
				DataRecordLinkSpy::new);
		MRV.setDefaultReturnValuesSupplier("factorResourceLinkUsingNameInDataAndMimeType",
				DataResourceLinkSpy::new);
		MRV.setDefaultReturnValuesSupplier("factorAtomicUsingNameInDataAndValue",
				DataAtomicSpy::new);
		MRV.setDefaultReturnValuesSupplier("factorAtomicUsingNameInDataAndValueAndRepeatId",
				DataAtomicSpy::new);
		MRV.setDefaultReturnValuesSupplier("factorAttributeUsingNameInDataAndValue",
				DataAttributeSpy::new);
		MRV.setDefaultReturnValuesSupplier("factorDataChildFilterUsingNameInData",
				DataChildFilterSpy::new);
	}

	@Override
	public DataList factorListUsingNameOfDataType(String nameOfDataType) {
		return (DataList) MCR.addCallAndReturnFromMRV("nameOfDataType", nameOfDataType);
	}

	@Override
	public DataRecord factorRecordUsingDataRecordGroup(DataRecordGroup dataRecordGroup) {
		return (DataRecord) MCR.addCallAndReturnFromMRV("dataRecordGroup", dataRecordGroup);
	}

	@Override
	public DataRecordGroup factorRecordGroupUsingNameInData(String nameInData) {
		return (DataRecordGroup) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@Override
	public DataRecordGroup factorRecordGroupFromDataGroup(DataGroup dataGroup) {
		return (DataRecordGroup) MCR.addCallAndReturnFromMRV("dataGroup", dataGroup);
	}

	@Override
	public DataGroup factorGroupFromDataRecordGroup(DataRecordGroup dataRecordGroup) {
		return (DataGroup) MCR.addCallAndReturnFromMRV("dataRecordGroup", dataRecordGroup);
	}

	@Override
	public DataGroup factorGroupUsingNameInData(String nameInData) {
		return (DataGroup) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@Override
	public DataRecordLink factorRecordLinkUsingNameInData(String nameInData) {
		return (DataRecordLink) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

	@Override
	public DataRecordLink factorRecordLinkUsingNameInDataAndTypeAndId(String nameInData,
			String recordType, String recordId) {
		return (DataRecordLink) MCR.addCallAndReturnFromMRV("nameInData", nameInData, "recordType",
				recordType, "recordId", recordId);
	}

	@Override
	public DataResourceLink factorResourceLinkUsingNameInDataAndMimeType(String nameInData,
			String type, String id, String mimeType) {
		return (DataResourceLink) MCR.addCallAndReturnFromMRV("nameInData", nameInData,
				"recordType", type, "recordId", id, "mimeType", mimeType);
	}

	@Override
	public DataAtomic factorAtomicUsingNameInDataAndValue(String nameInData, String value) {
		return (DataAtomic) MCR.addCallAndReturnFromMRV("nameInData", nameInData, "value", value);
	}

	@Override
	public DataAtomic factorAtomicUsingNameInDataAndValueAndRepeatId(String nameInData,
			String value, String repeatId) {
		return (DataAtomic) MCR.addCallAndReturnFromMRV("nameInData", nameInData, "value", value,
				"repeatId", repeatId);
	}

	@Override
	public DataAttribute factorAttributeUsingNameInDataAndValue(String nameInData, String value) {
		return (DataAttribute) MCR.addCallAndReturnFromMRV("nameInData", nameInData, "value",
				value);
	}

	@Override
	public DataChildFilter factorDataChildFilterUsingNameInData(String childNameInData) {
		return (DataChildFilter) MCR.addCallAndReturnFromMRV("childNameInData", childNameInData);
	}
}
