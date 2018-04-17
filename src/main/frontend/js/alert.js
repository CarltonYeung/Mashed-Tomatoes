module.exports.init = () => {
  $('#alert-success').on('close.bs.alert', evt => {
    evt.preventDefault();
    $(evt.currentTarget).prop('hidden', true);
  });

  $('#alert-danger').on('close.bs.alert', evt => {
    evt.preventDefault();
    $(evt.currentTarget).prop('hidden', true);
  });
};

module.exports.display = (message, isDanger) => {
  const alertSelector = $(isDanger ? '#alert-danger' : '#alert-success');
  alertSelector.find('strong').text(message);
  alertSelector.prop('hidden', false);
}