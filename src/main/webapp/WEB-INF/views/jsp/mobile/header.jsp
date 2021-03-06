<div id="menuPanel" class="menuPanel">
    <div id="menuContentPanel">
        <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
        <div id="logoBlock" class="menuBlock">
            <a href="${urlPrefix}/?m=true" id="logo">Schedule<br><span id="logoDot">.</span>Online</a>
        </div>
        <div id="accountBlock" class="menuBlock">
            <a id="accountLogout" href="${urlPrefix}/logout"><img src="${urlPrefix}/resources/icon/logout32-4.png"></a>
            <a href="${urlPrefix}/account?m=true" id="accountName">FIRSTNAME<br>SECONDNAME</a>
        </div>
        <div id="groupBlock" class="menuBlock">
            <a id="groupName" href="${urlPrefix}/group?m=true" id="groupName">GROUP</a>
        </div>

        <div id="filterBlock" class="menuBlock menuRegularBlock">
            <form>
                <p class="menuHeader">Фильтры</p>
                <label for="lessonFilter">Занятия</label>
                <input id="lessonFilter" type="checkbox" onchange="saveFilter(); loadData(); showRecords();" checked>
                <br>
                <label for="eventFilter">События</label>
                <input id="eventFilter" type="checkbox" onchange="saveFilter(); loadData(); showRecords();" checked>
                <br>
                <label for="searchFilter">Поиск</label>
                <input id="searchFilter" type="text" onchange="saveFilter(); loadData(); showRecords();">
                <br>
                <label for="tagsFilter">Теги</label>
                <input id="tagsFilter" type="text" onchange="saveFilter(); loadData(); showRecords();">
                <br>
                <label for="hideLastFilter">Скрыть прошедшее</label>
                <input id="hideLastFilter" type="checkbox" onchange="saveFilter(); loadData(); showRecords();">
                <br>
            </form>
        </div>

        <div id="actionBlock" class="menuBlock menuRegularBlock">
            <p class="menuHeader">Действия</p>
            <div id="addLessonBtn" >
                <a href="${urlPrefix}/new-lesson?m=true" class="menuLink">Добавить занятие</a>
                <br>
            </div>
            <div id="addEventBtn" >
                <a href="${urlPrefix}/new-event?m=true" class="menuLink">Добавить событие</a>
                <br>
            </div>
            <div id="curDayBtn">
                <a href="#" onclick="scrollToToday()" class="menuLink">Перейти к текущему дню</a>
                <br>
            </div>
        </div>
    </div>
</div>

<script>
    const account = JSON.parse('${accountDTO}');
    $('#accountName').html(account.firstName + '<br>' + account.secondName);
    $('#accountBlock').css('visibility', 'visible');

    const group = JSON.parse('${groupDTO}');
    if(group != null) {
        $('#groupName').text(group.name);
        $('#groupBlock').css('visibility', 'visible');
    }
</script>

<script>
    function openNav() {
        setBlockDisplaying('menuPanel', true);
    }

    function closeNav() {
        setBlockDisplaying('menuPanel', false);
    }
</script>