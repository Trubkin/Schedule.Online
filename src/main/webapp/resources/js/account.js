var currentAccount = {};

function initAccountPage() {
    currentAccount = account;
    putAccountToFields();
}

function readAccountFromFields() {
    currentAccount.firstName = $('#firstNameFld').val();
    currentAccount.secondName = $('#secondNameFld').val();

    var emailFld = $('#emailFld');
    if(emailFld === '') {
        currentAccount.email = null;
    }
    else {
        currentAccount.email = emailFld.val();
    }

    var phoneNumberFld = $('#phoneNumberFld');
    if(phoneNumberFld.val() === '') {
        currentAccount.phoneNumber = null;
    }
    else {
        currentAccount.phoneNumber = phoneNumberFld.val();
    }

    currentAccount.settingsNotification = $('#settingsNotificationCheckbox').prop('checked');
    currentAccount.scheduleNotification = $('#scheduleNotificationCheckbox').prop('checked');
}

function putAccountToFields() {
    $('#firstNameFld').val(currentAccount.firstName);
    $('#secondNameFld').val(currentAccount.secondName);
    $('#emailFld').val(currentAccount.email);
    $('#phoneNumberFld').val(currentAccount.phoneNumber);
    $('#settingsNotificationCheckbox').prop('checked', currentAccount.settingsNotification);
    $('#scheduleNotificationCheckbox').prop('checked', currentAccount.scheduleNotification);
}

function saveAccount() {
    readAccountFromFields();
    $.ajax({
        type: 'PUT',
        url: urlPrefix + '/api/account',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(currentAccount),
        success: function (result) {
            alert('Аккаунт успешно обновлен');
            window.location.href = urlPrefix + "/";
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + errorThrown);
        }
    });
}