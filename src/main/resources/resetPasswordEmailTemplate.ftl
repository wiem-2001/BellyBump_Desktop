<!DOCTYPE html>
<html>
<head>
    <title>Password Reset</title>
    <style>
        /* Add styles for the pink rectangle */
        .pink-rectangle {
            background-color: #FFC0CB; /* Pink color */
            color: white; /* White text */
            padding: 10px; /* Add padding */
            font-size: 24px; /* Adjust font size */
            text-align: center; /* Center text */
        }
    </style>
</head>
<body>
<div class="pink-rectangle">BellyBump</div>
<p style="font-size: 18px">
    Dear ${userName},<br><br>
    You have requested to <span style="color: #B22222; font-weight: bold" >reset your password </span>for your account with <strong>BellyBump</strong>.<br/><br/>
    Enter this code in the required field: <span style="color: #B22222; font-weight: bolder" >${resetToken}</span><br/><br/>
    If you did not request a password reset, please ignore this email.<br/><br/>
    Best regards,<br/>
    - BellyBump
</p>
</body>
</html>
