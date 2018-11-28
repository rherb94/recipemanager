
var recipesTable;
var selectedRecipeId;

$(document).ready(function() {
    $("#addRecipeButton").button();
    $("#modifyRecipeButton").button().hide();
    $("#deleteRecipeButton").button().hide();

    recipesTable = $('#recipesTable').DataTable({select: true});

    $("#recipesTable tbody").on('click', 'tr', function(e) {
        if ( $(this).hasClass('row_selected') ) {
            selectedRecipeId = 0;
            $(this).removeClass('row_selected');
            $("#addRecipeButton").show();
            $("#modifyRecipeButton").hide();
            $("#deleteRecipeButton").hide();
        }
        else {
            var ida = this.id.split("_");
            selectedRecipeId = ida[1];
            recipesTable.$('tr.row_selected').removeClass('row_selected');
            $(this).addClass('row_selected');
            $("#addRecipeButton").hide();
            $("#modifyRecipeButton").show();
            $("#deleteRecipeButton").show();
        }
    });

    $('#recipeFormModal').on('shown.bs.modal', function () {
        $('#recipeFormModal').validator({delay: 750, disable: true});
    });

    $('#recipeFormModal').on('hidden.bs.modal', function () {
        $('#recipeFormModal').validator('destroy');
    });

    $.get('recipes', function(data) {processRecipeList(data);});
});

function processRecipeList(recipes) {
    $.each(recipes, function(i, recipe) {
        addRecipeRow(recipe);
    });
}

function addRecipeRow(recipe) {
    var newRow = recipesTable.row.add([recipe.title, recipe.contributorName, recipe.instructions, recipe.notes]).draw();

    newRow.node().id = "row_" + recipe.id;
    $('td:eq(0)', newRow.node()).attr('id', "title_" + recipe.id);
    $('td:eq(1)', newRow.node()).attr('id', "contributorName_" + recipe.id);
    $('td:eq(2)', newRow.node()).attr('id', "instructions_" + recipe.id);
    $('td:eq(3)', newRow.node()).attr('id', "notes_" + recipe.id);
}

function createRecipe() {
    $('#recipeForm')[0].reset();
    $("#recipeId").val("");
    $('#recipFormTitle').html("Create a new Recipe");
    $('#recipeFormModal').modal('show');
}

function modifySelectedRecipe() {
    $.get('recipes/' + selectedRecipeId, function(recipe) {
        $("#recipeId").val(recipe.id);
        $("#contributorName").val(recipe.contributorName);
        $("#title").val(recipe.title);
        $("#instructions").val(recipe.instructions);
        $("#notes").val(recipe.notes);

        $('#recipFormTitle').html("Modify Recipe");
        $('#recipeFormModal').modal('show');
    });
}

function deleteSelectedRecipe() {
    var title = $("#title_" + selectedRecipeId).text();
    bootbox.confirm({
        size: 'small',
        message: "Are you sure you want to delete Recipe <b style='color:red;'>" + title + "</b> ?",
        callback: function(result) {
            if (result) {
                var url = 'recipes/' + selectedRecipeId;

                $.ajax({
                    type: 'DELETE',
                    url: url,
                    success: function(data) {
                        recipesTable.row('.selected').remove();
                        recipesTable.search('').draw();
                        $("#addRecipeButton").button().show();
                        $("#modifyRecipeButton").button().hide();
                        $("#deleteRecipeButton").button().hide();
                    },
                    error : function(request, status, error) {
                        console.log(JSON.stringify(recipe));
                        alert("Failed: " + error);
                    }
                });
            }
        }
    });


}

function saveRecipe() {
    var recipe = new Object();
    recipe.id = $("#recipeId").val();
    recipe.contributorName = $("#contributorName").val();
    recipe.title = $("#title").val();
    recipe.instructions = $("#instructions").val();
    recipe.notes = $("#notes").val();

    if (!recipe.contributorName || !recipe.title || !recipe.instructions) {
        $('#recipeFormModal').validator('validate');
        return;
    }

    var type = recipe.id  === "" ? "POST" : "PUT";
    var url = recipe.id === "" ? "recipes" : "recipes/" + recipe.id;
    $.ajax({
        type: type,
        url: url,
        contentType: "application/json",
        data: JSON.stringify(recipe),
        success: function(data) {
            processSubmitResults(data, type);
        },
        error : function(request, status, error) {
            console.log(JSON.stringify(recipe));
            alert("Failed: " + error);
        }
    });
}

function processSubmitResults(recipe, type) {
    if (type === "POST") {
        addRecipeRow(recipe);
    }
    else {
        $("#title_" + recipe.id).text(recipe.title);
        $("#contributorName_" + recipe.id).text(recipe.contributorName);
        $("#instructions_" + recipe.id).text(recipe.instructions);
        $("#notes_" + recipe.id).text(recipe.notes);
    }
    $('#recipeFormModal').modal('hide');
}