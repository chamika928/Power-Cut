//Hide the alerts on page load
$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}
	
	$("#alertError").hide();
});
// SAVE
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts
	 $("#alertSuccess").text("");
	 $("#alertSuccess").hide();
	 $("#alertError").text("");
	 $("#alertError").hide();
	 
	// Form validation
	var status = validateItemForm();
	if (status != true)
	 {
		 $("#alertError").text(status);
		 $("#alertError").show();
		 return;
	 }	
	// If valid
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
		{
			url : "PowerCutsAPI",
			type : type,
			data : $("#formPowerCut").serialize(),
			dataType : "text",
			complete : function(response, status)
			{
				onItemSaveComplete(response.responseText, status);
			}
		});
});

// UPDATE
$(document).on("click", ".btnUpdate", function(event)
{		
	
	 $("#hidItemIDSave").val($(this).data("powerid"));
	 $("#PowerCutProvince").val($(this).closest("tr").find('td:eq(0)').text());
	 $("#PowerCutCity").val($(this).closest("tr").find('td:eq(1)').text());
	 $("#PowerCutDate").val($(this).closest("tr").find('td:eq(2)').text());
	 $("#PowerCutTime").val($(this).closest("tr").find('td:eq(3)').text());
});

//DELETE
$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
		{
			url : "PowerCutsAPI",
			type : "DELETE",
			data : "PowerCutID=" + $(this).data("powerid"),
			dataType : "text",
			complete : function(response, status)
			{
				onItemDeleteComplete(response.responseText, status);
			}
		});
});








// CLIENT-MODEL
function validateItemForm()
{
	// CODE
	if ($("#PowerCutProvince").val().trim() == "")
	 {
		return "Insert Province.";
	 }
	// NAME
	if ($("#PowerCutCity").val().trim() == "")
	 {
		return "Insert City.";
	 }
	
	// NAME
	if ($("#PowerCutDate").val().trim() == "")
	 {
		return "Insert City.";
	 }
	
	// DESCRIPTION
	if ($("#PowerCutTime").val().trim() == "")
	 {
		return "Insert Item Description.";
	 }
	return true;
}
function onItemSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	
	else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}
function onItemDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}