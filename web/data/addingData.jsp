<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>მონაცემების შეყვანა</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet"
    href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="addingDataStyle.css">
  <script
    src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script
    src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="addingDataScript.js"></script>

</head>
<body>

  <div align="center" id="main-div">
    <button id="button1">ლექციების ცხრილის შეყვანა</button> 
    <div id="schedule" class="to-hide">
      <br><br><br>
      <label id="info-label"><b>შეგიძლიათ მონაცემები შეიყვანოთ ხელით (სათითაოდ):</b></label> 
      <br><br>
      <form id="sched-form" action="#" class="form-vertical">

        <div class="form-group">
          <input type="text" name="lecturer_name" class="form-control"
            placeholder="შეიყვანეთ ლექტორის სახელი და გვარი">
        </div>

        <div class="form-group">
          <input type="text" name="room_number" class="form-control"
            placeholder="შეიყვანეთ ოთახის ნომერი">
        </div>

        <div class="form-group">
          <input type="text" name="subject" class="form-control"
            placeholder="შეიყვანეთ საგნის სახელი">
        </div>
        
        <div class="select">
          <label class="control-label">კვირის დღე</label>
          <select name="week_day" class="form-control">
            <option value="monday">ორშაბათი</option>
            <option value="tuesday">სამშაბათი</option>
            <option value="wednesday">ოთხშაბათი</option>
            <option value="thursday">ხუთშაბათი</option>
            <option value="friday">პარასკევი</option>
            <option value="saturday">შაბათი</option>
            <option value="sunday">კვირა</option>
          </select>
        </div>
        
        <div class="form-group">
          <label class="control-label">შეიყვანეთ ლექციის დაწყების დრო</label>
          <input type="time" name="start_time" class="form-control">
        </div>
        <div class="form-group">
          <label class="control-label">შეიყვანეთ ლექციის დამთავრების დრო</label>
          <input type="time" name="end_time" class="form-control">
        </div>
        
        <input type="submit" value="დამატება" class="btn btn-primary btn-lg">   
      </form>
      
      <br><br><br>
      <label id="info-label"><b>ან ატვირთოთ Excel-ის ფაილი:</b></label>
      <br><br>
      
      <form action="#" class="form-vertical" id="upload-form">
        <input type="file" name="pic" accept=".xls,.xlsx"><br>
        <input type="submit" value="ატვირთვა" class="btn btn-primary btn-lg">
      </form>
      <br><br>
    </div>
    <br><br><br>
    
    <button id="button2">ოთახის დამატება</button> 
    <div id="add-room-div" class="to-hide">
      <br><br><br>
      <form id="add-room-form" action="#" class="form-vertical">

        <div class="form-group">
          <label class="control-label">ოთახის სახელი</label>
          <input type="text" name="room_name"
                 class="form-control" placeholder="შეიყვანეთ ოთახის სახელი">
        </div>

        <div class="form-group">
          <label class="control-label">სართული</label> 
          <input type="number" name="room_floor" class="form-control"
                placeholder="შეიყვანეთ სართული">
        </div>

        <div class="form-group">
          <label class="control-label">ადგილების რაოდენობა</label>
          <input type="number" name="capacity" class="form-control"
                 placeholder="შეიყვანეთ ადგილების რაოდენობა"> 
        </div>

        <div class="select">
          <label class="control-label">ოთახის ტიპი</label>
          <select name="room_type" class="form-control">
            <option value="any">ყველა</option>
            <option value="auditorium">აუდიტორია</option>
            <option value="utility">სხვა</option>
          </select>
        </div>

        <div class="select">
          <label class="control-label">ადგილების ტიპი</label>
          <select name="seat_type" class="form-control">
            <option value="any">ყველანაირი</option>
            <option value="desks">სკამები და მერხები</option>
            <option value="wooden_chair">სკამ-მერხები (ხის)</option>
            <option value="plastic_chair">სკამ-მერხები
              (პლასტმასის)
            </option>
            <option value="computers">კომპიუტერები</option>
            <option value="tables">მაგიდები</option>
          </select>
        </div>

        <div class="checkbox">
          <label class="control-label">
            <input type="checkbox" name="can_be_booked"> შეიძლება სტუდენტისთვის
          </label>
        </div>

        <input type="submit" value="დამატება" class="btn btn-primary btn-lg">
      </form>
    </div>
    <br><br><br>
    
    <button id="button3">ოთახის წაშლა</button> 
    <div id="remove-room-div" class="to-hide">
      <br><br><br>
      <form id="add-room-form" action="#" class="form-vertical">

        <div class="form-group">
          <label class="control-label">ოთახის სახელი</label>
          <input type="text" name="room_name"
                 class="form-control" placeholder="შეიყვანეთ ოთახის სახელი">
        </div>
        
        <input type="submit" value="წაშლა" class="btn btn-primary btn-lg">
      </form>
    </div>
    <br><br><br>
  </div>

</body>
</html>