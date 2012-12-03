/**
License: GPL 2.0

Copyright (C) 2012 Benson Trinh, Thomas Polasek, Remco Uittenbogerd, Clinton Wong

This program is free software; you can redistribute it and/or modify it under the 
terms of the GNU General Public License as published by the Free Software Foundation;

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; 
if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
MA 02110-1301, USA.


This class is used to retrieve the data from the JSON Server provided in this class. It is
used to store the values given a JSON formatted string. Using this class makes it easier to
use Gson. 

@author Benson Trinh
 **/
package com.cmput301.classproject.Model.Tasks;

import com.cmput301.classproject.Model.Task;

public class ServerData {

	private Task content;
	private String id;
	
	public ServerData() {}
	
	/**
	 * 
	 * @return The task contained in this Server Data object
	 */
	public Task getContent() {
		return content;
	}

	/**
	 * 
	 * @param content The content to set for this Server Data object
	 */
	public void setContent(Task content) {
		this.content = content;
	}

	/**
	 * 
	 * @return The id for this Server Data object
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id The ID to set for this Server Data object
	 */
	public void setId(String id) {
		this.id = id;
	}


}