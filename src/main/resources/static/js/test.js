function gf_modal(as_url,as_acd,as_bcd,as_ccd,as_action,as_val) {
    $.ajax({
        url:as_url,
        type:"GET",
        data: { ACD : as_acd ,BCD : as_bcd ,CCD : as_ccd ,action : as_action ,vseq : as_val},
        dataType:"html",
        success: function(req,stat) {
            if(stat=="success") {
                // console.log(req);
                $('#modal').html(req);
            }
        },
        error: (log)=>{alert("실패" + log)},
    });
}
// function gf_menu(as_acd,as_bcd,as_ccd,as_empno) {
//     var purl = "menu.php";
//     $.ajax({
//         url:purl,
//         type:"GET",
//         data: { ACD : as_acd ,BCD : as_bcd ,CCD : as_ccd ,sj_userid : as_empno},
//         dataType:"html",
//         complete: function(req,stat) {
//             if(stat=="success") {
//                 $('#sb-sidenav-menu').html(req.responseText);
//             }
//         },
//         error: function( req, errortype) {
//             if (typeof view_error == 'function')
//                 view_error(req.status+" : "+req.statusText+"<br/>"+req.responseText,errortype);
//             else
//                 alert(req.status+" : "+req.statusText+" "+req.responseText,errortype)
//         }
//     });
// }