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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import se.uu.ub.cora.data.Action;
import se.uu.ub.cora.data.DataAttribute;
import se.uu.ub.cora.data.DataResourceLink;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;

public class DataResourceLinkSpy implements DataResourceLink {
	public MethodCallRecorder MCR = new MethodCallRecorder();
	public MethodReturnValues MRV = new MethodReturnValues();

	public DataResourceLinkSpy() {
		MCR.useMRV(MRV);
		MRV.setDefaultReturnValuesSupplier("hasReadAction", () -> false);
		MRV.setDefaultReturnValuesSupplier("hasRepeatId", () -> false);
		MRV.setDefaultReturnValuesSupplier("getType", () -> "someType");
		MRV.setDefaultReturnValuesSupplier("getId", () -> "someId");
		MRV.setDefaultReturnValuesSupplier("getRepeatId", String::new);
		MRV.setDefaultReturnValuesSupplier("getNameInData", String::new);
		MRV.setDefaultReturnValuesSupplier("hasAttributes", () -> false);
		MRV.setDefaultReturnValuesSupplier("getAttribute", DataAttributeSpy::new);
		MRV.setDefaultReturnValuesSupplier("getAttributes", ArrayList<DataAttribute>::new);
		MRV.setDefaultReturnValuesSupplier("getMimeType", String::new);
		MRV.setDefaultReturnValuesSupplier("getAttributeValue", Optional::empty);
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
	public void addAction(Action action) {
		MCR.addCall("action", action);
	}

	@Override
	public boolean hasReadAction() {
		return (boolean) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public boolean hasRepeatId() {
		return (boolean) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void setRepeatId(String repeatId) {
		MCR.addCall("repeatId", repeatId);
	}

	@Override
	public String getRepeatId() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public String getNameInData() {
		return (String) MCR.addCallAndReturnFromMRV();
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

	@Override
	public Collection<DataAttribute> getAttributes() {
		return (Collection<DataAttribute>) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void setMimeType(String mimeType) {
		MCR.addCall("mimeType", mimeType);
	}

	@Override
	public String getMimeType() {
		return (String) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public Optional<String> getAttributeValue(String nameInData) {
		return (Optional<String>) MCR.addCallAndReturnFromMRV("nameInData", nameInData);
	}

}
