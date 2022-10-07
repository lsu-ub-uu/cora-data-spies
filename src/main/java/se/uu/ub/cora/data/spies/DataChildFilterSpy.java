/*
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

import java.util.Set;
import java.util.function.Supplier;

import se.uu.ub.cora.data.DataChild;
import se.uu.ub.cora.data.DataChildFilter;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;

public class DataChildFilterSpy implements DataChildFilter {

	public MethodCallRecorder MCR = new MethodCallRecorder();
	public MethodReturnValues MRV = new MethodReturnValues();

	public DataChildFilterSpy() {
		MCR.useMRV(MRV);
		MRV.setDefaultReturnValuesSupplier("childMatches", (Supplier<Boolean>) () -> true);
	}

	@Override
	public void addAttributeUsingNameInDataAndPossibleValues(String nameInData,
			Set<String> possibleValues) {
		MCR.addCall("nameInData", nameInData, "possibleValues", possibleValues);
	}

	@Override
	public boolean childMatches(DataChild child) {
		return (boolean) MCR.addCallAndReturnFromMRV("child", child);
	}

}
