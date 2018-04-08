<!DOCTYPE html>
<html>

<head>
    @@include('meta.html')
    <title>User Page</title>
    @@include('style.html')
</head>

<body>
    @@include('header.html')
    <main class="container">
        <h1 class="main-title">John Doe</h1>
        <div class="row">
            <ul class="nav nav-pills mx-auto user-tab" id="pills-tab">
                <li class="pr-3 nav-item user-tab -item">
                    <a class="nav-link active user-tab -item-link" data-toggle="pill" href="#pills-account">Account</a>
                </li>
                <li class="px-3 nav-item user-tab -item">
                    <a class="nav-link user-tab -item-link" data-toggle="pill" href="#pills-about">About</a>
                </li>
                <li class="px-3 nav-item user-tab -item">
                    <a class="nav-link user-tab -item-link" id="pills-ratings-tab" data-toggle="pill" href="#pills-ratings">Ratings</a>
                </li>
                <li class="px-3 nav-item user-tab -item">
                    <a class="nav-link user-tab -item-link" id="pills-lists-tab" data-toggle="pill" href="#pills-lists">WTS/NI</a>
                </li>
                <li class="px-3 nav-item user-tab -item">
                    <a class="nav-link user-tab -item-link" id="pills-quotes-tab" data-toggle="pill" href="#pills-quotes">Quotes</a>
                </li>
                <li class="pl-3 nav-item user-tab -item">
                    <a class="nav-link user-tab -item-link" id="pills-critics-tab" data-toggle="pill" href="#pills-stats">Stats</a>
                </li>
            </ul>
        </div>
        <div class="tab-content" id="pills-tabContent">
            <div class="tab-pane fade show active user-pane" id="pills-account" role="tabpanel">
                @@include('user-pills/account.html')
            </div>
            <div class="tab-pane fade user-pane" id="pills-about" role="tabpanel">
                @@include('user-pills/about.html')
            </div>
            <div class="tab-pane fade user-pane" id="pills-ratings" role="tabpanel">
                @@include('user-pills/ratings.html')
            </div>
            <div class="tab-pane fade user-pane" id="pills-lists" role="tabpanel">
                @@include('user-pills/lists.html')
            </div>
            <div class="tab-pane fade user-pane" id="pills-quotes" role="tabpanel">
                @@include('user-pills/quotes.html')
            </div>
            <div class="tab-pane fade user-pane" id="pills-stats" role="tabpanel">
                @@include('user-pills/stats.html')
            </div>
        </div>
    </main>
    @@include('footer.html') @@include('script.html')
</body>

</html>