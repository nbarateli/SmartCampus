<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
  <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/jszip.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/xlsx.js"></script>
  <script src="ExcelReader.js"></script>
</head>
<body>

<div align="center" id="main-div">
  <button>ლექციების ცხრილის შეყვანა</button>
  <div id="schedule" class="to-hide">
    <br><br><br>
    <label id="info-label"><b>შეგიძლიათ მონაცემები შეიყვანოთ ხელით (სათითაოდ):</b></label>
    <br><br>
    <form id="sched-form" action="../LectureAdder" class="form-vertical" method="get">

      <div class="form-group">
        <input type="text" name="lecturer_name" class="form-control"
               placeholder="შეიყვანეთ ლექტორის სახელი და გვარი">
      </div>

      <div class="form-group">
        <input type="text" name="room_name" class="form-control"
               placeholder="შეიყვანეთ ოთახის დასახელება">
      </div>

      <div class="form-group">
        <input name="subject" class="form-control"
               placeholder="შეიყვანეთ საგნის სახელი">
      </div>

      <div class="select">
        <label class="control-label">ლექციის დრო</label>
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
      <br>
      <div class="form-group">

        <input type="text" name="start_time" class="form-control"
               placeholder="შეიყვანეთ ლექციის დაწყების დრო(HH:mm 24-საათიანი ფორმატით)">
      </div>
      <div class="form-group">

        <input type="text" name="end_time" class="form-control"
               placeholder="შეიყვანეთ ლექციის დასრულების დრო(HH:mm 24-საათიანი ფორმატით)">
      </div>

      <input type="submit" value="დამატება" class="btn btn-primary btn-lg">
    </form>

    <br><br><br>
    <label id="info-label2"><b>ან ატვირთოთ Excel-ის ფაილი:</b></label>
    <br><br>
    <script></script>
    <form action="#" class="form-vertical" id="file" method="get">
      <input type="file" name="pic" accept=".xls,.xlsx"><br>
      <output id="list"></output>

      <script>
          function handleFileSelect(evt) {
              var files = evt.target.files; // FileList object
              console.log("ra xdebaaa");
              // files is a FileList of File objects. List some properties.
              var output = [];
              for (var i = 0, f; f = files[i]; i++) {
                  console.log(f);
                  ExcelToJSON(f);
//                  output.push('<li><strong>', escape(f.name), '</strong> (', f.type || 'n/a', ') - ',
//                      f.size, ' bytes, last modified: ',
//                      f.lastModifiedDate ? f.lastModifiedDate.toLocaleDateString() : 'n/a',
//                      '</li>');
              }

              document.getElementById('list').innerHTML = '<ul>' + output.join('') + '</ul>';
          }

          document.getElementById('file').addEventListener('change', handleFileSelect, false);
      </script>
      <input type="submit" value="ატვირთვა" class="btn btn-primary btn-lg">
    </form>
    <br><br>
  </div>
  <br><br><br>

  <button>ოთახის დამატება</button>
  <div id="add-room-div" class="to-hide">
    <br><br><br>
    <form id="add-room-form" action="../RoomAdder" class="form-vertical" method="get">

      <div class="form-group">
        <label class="control-label">ოთახის სახელი</label>
        <input type="text" name="room_name"
               class="form-control" placeholder="შეიყვანეთ ოთახის სახელი">
      </div>

      <div class="form-group">
        <label class="control-label">სართული</label>
        <input type="number" name="room_floor" class="form-control"
               placeholder="შეიყვანეთ სართული" min=0 max=4>
      </div>

      <div class="form-group">
        <label class="control-label">ადგილების რაოდენობა</label>
        <input type="number" name="capacity" class="form-control"
               placeholder="შეიყვანეთ ადგილების რაოდენობა" min=0>
      </div>

      <div class="select">
        <label class="control-label">ოთახის ტიპი</label>
        <select name="room_type" class="form-control">
          <option value="auditorium">აუდიტორია</option>
          <option value="utility">სხვა</option>
        </select>
      </div>

      <div class="select">
        <label class="control-label">ადგილების ტიპი</label>
        <select name="seat_type" class="form-control">
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

  <button>ოთახის წაშლა</button>
  <div id="remove-room-div" class="to-hide">
    <br><br><br>
    <form id="remove-room-form" action="../RoomRemover" class="form-vertical" method="get">

      <div class="form-group">
        <input type="text" name="room_name"
               class="form-control" placeholder="შეიყვანეთ ოთახის სახელი">
      </div>

      <input type="submit" value="წაშლა" class="btn btn-primary btn-lg">
    </form>
  </div>
  <br><br><br>

  <button>საგნის დამატება</button>
  <div id="add-subj-div" class="to-hide">
    <br><br><br>
    <form id="add-subj-form" action="#" class="form-vertical" method="get">

      <div class="form-group">
        <input type="text" name="subj_name"
               class="form-control" placeholder="შეიყვანეთ საგნის სახელი">
      </div>

      <input type="submit" value="დამატება" class="btn btn-primary btn-lg">
    </form>
  </div>
  <br><br><br>
</div>

</body>
</html>