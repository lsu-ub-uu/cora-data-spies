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

import java.util.ArrayList;
import java.util.List;

import se.uu.ub.cora.data.Data;
import se.uu.ub.cora.data.DataList;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;

@SuppressWarnings("exports")
public class DataListSpy implements DataList {
	public MethodCallRecorder MCR = new MethodCallRecorder();
	public MethodReturnValues MRV = new MethodReturnValues();

	public DataListSpy() {
		MCR.useMRV(MRV);
		MRV.setDefaultReturnValuesSupplier("getFromNo", String::new);
		MRV.setDefaultReturnValuesSupplier("getToNo", String::new);
		MRV.setDefaultReturnValuesSupplier("getTotalNumberOfTypeInStorage", String::new);
		MRV.setDefaultReturnValuesSupplier("getContainDataOfType", String::new);
		MRV.setDefaultReturnValuesSupplier("getDataList", ArrayList<Data>::new);
	}

	@Override
	public String getFromNo() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public String getToNo() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public String getTotalNumberOfTypeInStorage() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public String getContainDataOfType() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public List<Data> getDataList() {
		return (List<Data>) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void addData(Data data) {
		MCR.addCall("data", data);
	}

	@Override
	public void setFromNo(String position) {
		MCR.addCall("position", position);
	}

	@Override
	public void setToNo(String position) {
		MCR.addCall("position", position);
	}

	@Override
	public void setTotalNo(String totalNumber) {
		MCR.addCall("totalNumber", totalNumber);
	}
}
